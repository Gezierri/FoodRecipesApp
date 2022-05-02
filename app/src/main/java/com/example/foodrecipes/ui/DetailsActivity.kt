package com.example.foodrecipes.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.navArgs
import com.example.foodrecipes.R
import com.example.foodrecipes.adapters.PageAdapter
import com.example.foodrecipes.data.database.entities.FavoriteEntity
import com.example.foodrecipes.databinding.ActivityDetailsBinding
import com.example.foodrecipes.ui.fragments.ingredients.IngredientsFragment
import com.example.foodrecipes.ui.fragments.instructions.InstructionsFragment
import com.example.foodrecipes.ui.fragments.overview.OverviewFragment
import com.example.foodrecipes.utils.Constants.Companion.RECIPES_BUNDLE
import com.example.foodrecipes.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private val args by navArgs<DetailsActivityArgs>()
    private lateinit var binding: ActivityDetailsBinding

    private val mainViewModel: MainViewModel by viewModels()

    private var savedRecipeId = 0
    private var savedRecipes = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val titles = ArrayList<String>()
        titles.add("Override")
        titles.add("Ingredients")
        titles.add("Instruction")

        val resultBundle = Bundle()
        resultBundle.putParcelable(RECIPES_BUNDLE, args.result)


        val pageAdapter = PageAdapter(
            resultBundle,
            fragments,
            this
        )

        binding.viewPager2.isUserInputEnabled = false
        binding.viewPager2.apply {
            adapter = pageAdapter
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager2){tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
        }else if (item.itemId == R.id.save_to_favorites_menu && !savedRecipes){
            saveToFavorites(item)
        }else if(item.itemId == R.id.save_to_favorites_menu && savedRecipes){
            removeToFavorite(item)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        val menuItem = menu.findItem(R.id.save_to_favorites_menu)
        checkSavedRecipes(menuItem)
        return true
    }

    private fun checkSavedRecipes(menuItem: MenuItem?) {
        mainViewModel.readFavorites.observe(this) {favoriteEntity ->
            try {
                for (savedResult in favoriteEntity){
                    if(savedResult.result.recipeId == args.result.recipeId){
                        changeMenuItemColor(menuItem, R.color.yellow)
                        savedRecipes = true
                        savedRecipeId = savedResult.id
                    }else{
                        changeMenuItemColor(menuItem, R.color.white)
                    }
                }
            }catch (e: Exception){
                Log.d("DetailsActivity", e.message.toString())
            }
        }
    }

    private fun saveToFavorites(item: MenuItem) {
        val favoriteEntity = FavoriteEntity(
            id = 0,
            args.result
        )
        mainViewModel.insertFavorite(favoriteEntity)
        changeMenuItemColor(item, R.color.yellow)
        showSnackBar("Recipes saved.")
    }

    private fun removeToFavorite(item: MenuItem){
        val favoriteEntity = FavoriteEntity(
            savedRecipeId,
            args.result
        )
        mainViewModel.deleteFavorite(favoriteEntity)
        showSnackBar("Removed from favorites.")
        changeMenuItemColor(item, R.color.white)
        savedRecipes = false
    }

    private fun showSnackBar(message: String){
        Snackbar.make(
            binding.detailsLayout,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay"){}
            .show()
    }

    private fun changeMenuItemColor(item: MenuItem?, color: Int){
        item?.icon?.setTint(ContextCompat.getColor(this, color))
    }
}