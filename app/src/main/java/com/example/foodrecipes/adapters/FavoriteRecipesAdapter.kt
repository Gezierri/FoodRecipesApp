package com.example.foodrecipes.adapters

import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipes.R
import com.example.foodrecipes.data.database.entities.FavoriteEntity
import com.example.foodrecipes.databinding.FavoriteRecipesRowLayoutBinding
import com.example.foodrecipes.ui.fragments.favorite.FavoriteFragmentDirections
import com.example.foodrecipes.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class FavoriteRecipesAdapter(
    private val requireActivity: FragmentActivity,
    private val mainViewModel: MainViewModel
) : RecyclerView.Adapter<FavoriteRecipesAdapter.MyViewHolder>(), ActionMode.Callback {

    private var multiSelection = false

    private var selectList = arrayListOf<FavoriteEntity>()
    private var myViewHolderList = arrayListOf<MyViewHolder>()
    private var favoriteList = emptyList<FavoriteEntity>()

    private lateinit var mActionMode: ActionMode
    private lateinit var rootView: View

    class MyViewHolder(val binding: FavoriteRecipesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteEntity: FavoriteEntity) {
            binding.favoritesEntity = favoriteEntity
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutBinding = LayoutInflater.from(parent.context)
                val binding = FavoriteRecipesRowLayoutBinding.inflate(layoutBinding, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        myViewHolderList.add(holder)
        rootView = holder.itemView.rootView

        val currentRecipe = favoriteList[position]
        holder.bind(currentRecipe)

        holder.binding.favoriteRecipesRowLayout.setOnClickListener {
            if (multiSelection) {
                applySelection(holder, currentRecipe)
            } else {
                val action =
                    FavoriteFragmentDirections.actionFavoriteFragmentToDetailsActivity(currentRecipe.result)
                holder.itemView.findNavController().navigate(action)
            }
        }
        holder.binding.favoriteRecipesRowLayout.setOnLongClickListener {
            if (!multiSelection){
                multiSelection = true
                requireActivity.startActionMode(this)
                applySelection(holder, currentRecipe)
                true
            }else{
                multiSelection = false
                false
            }
        }
    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }

    override fun onCreateActionMode(action: ActionMode?, menu: Menu?): Boolean {
        action?.menuInflater?.inflate(R.menu.favorite_contextual_menu, menu)
        mActionMode = action!!
        applyStatusBarColor(R.color.contextualStatusBarColor)
        return true
    }

    override fun onPrepareActionMode(action: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(action: ActionMode?, menu: MenuItem?): Boolean {
        if (menu?.itemId == R.id.delete_favorite_recipe_menu){
            selectList.forEach {
                mainViewModel.deleteFavorite(it)
            }
            setSnackBar("${selectList.size} Recipe/s removed.")

            multiSelection = false
            selectList.clear()
            mActionMode.finish()
        }
        return true
    }

    override fun onDestroyActionMode(action: ActionMode?) {
        selectList.clear()
        multiSelection = false
        applyStatusBarColor(R.color.statusBarColor)
        myViewHolderList.forEach {
            changeRecipeStyle(it, R.color.cardBackgroundColor, R.color.strokeColor)
        }
    }

    private fun applyStatusBarColor(color: Int) {
        requireActivity.window.statusBarColor = ContextCompat.getColor(requireActivity, color)
    }

    private fun applySelection(holder: MyViewHolder, currentRecipe: FavoriteEntity) {
        if (selectList.contains(currentRecipe)) {
            selectList.remove(currentRecipe)
            changeRecipeStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
            applyActionModeTitle()
        } else {
            selectList.add(currentRecipe)
            changeRecipeStyle(holder, R.color.cardBackgroundLightColor, R.color.colorPrimary)
            applyActionModeTitle()
        }
    }

    private fun changeRecipeStyle(holder: MyViewHolder, backgroundColor: Int, strokeColor: Int) {
        holder.binding.constraintBody.setBackgroundColor(
            ContextCompat.getColor(
                requireActivity,
                backgroundColor
            )
        )
        holder.binding.favoriteRowCardView.strokeColor = ContextCompat.getColor(
            requireActivity,
            strokeColor
        )
    }

    private fun applyActionModeTitle(){
        when(selectList.size){
            0 -> {
                mActionMode.finish()
            }
            1 -> {
                mActionMode.title = "${selectList.size} item selected"
            }
            2 -> {
                mActionMode.title = "${selectList.size} items selected"
            }
        }
    }

    private fun setSnackBar(msg: String){
        Snackbar.make(
            rootView,
            msg,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay"){}
            .show()
    }

    fun setData(favoriteEntityList: List<FavoriteEntity>) {
        val favoritesDiffUtil = RecipesDiffUtil(favoriteList, favoriteEntityList)
        val diffUtilResult = DiffUtil.calculateDiff(favoritesDiffUtil)
        favoriteList = favoriteEntityList
        diffUtilResult.dispatchUpdatesTo(this)
    }

    fun clearContextualActionMode(){
        if(this::mActionMode.isInitialized){
            mActionMode.finish()
        }
    }
}