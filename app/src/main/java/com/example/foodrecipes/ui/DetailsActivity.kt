package com.example.foodrecipes.ui

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.example.foodrecipes.R
import com.example.foodrecipes.adapters.PageAdapter
import com.example.foodrecipes.databinding.ActivityDetailsBinding
import com.example.foodrecipes.ui.fragments.ingredients.IngredientsFragment
import com.example.foodrecipes.ui.fragments.instructions.InstructionsFragment
import com.example.foodrecipes.ui.fragments.overview.OverviewFragment
import com.example.foodrecipes.utils.Constants.Companion.RECIPES_BUNDLE
import com.google.android.material.tabs.TabLayoutMediator

class DetailsActivity : AppCompatActivity() {

    private val args by navArgs<DetailsActivityArgs>()
    private lateinit var binding: ActivityDetailsBinding

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
        }
        return super.onOptionsItemSelected(item)
    }
}