package com.example.foodrecipes.adapters

import android.view.*
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipes.R
import com.example.foodrecipes.data.database.entities.FavoriteEntity
import com.example.foodrecipes.databinding.FavoriteRecipesRowLayoutBinding
import com.example.foodrecipes.ui.fragments.favorite.FavoriteFragmentDirections

class FavoriteRecipesAdapter(
    private val requireActivity: FragmentActivity
): RecyclerView.Adapter<FavoriteRecipesAdapter.MyViewHolder>(), ActionMode.Callback {

    private var favoriteList = emptyList<FavoriteEntity>()

    class MyViewHolder(val binding: FavoriteRecipesRowLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteEntity: FavoriteEntity){
            binding.favoritesEntity = favoriteEntity
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): MyViewHolder{
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
        val selectRecipe = favoriteList[position]
        holder.bind(selectRecipe)

        holder.binding.favoriteRecipesRowLayout.setOnClickListener {
            val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailsActivity(selectRecipe.result)
            holder.itemView.findNavController().navigate(action)
        }
        holder.binding.favoriteRecipesRowLayout.setOnLongClickListener {
            requireActivity.startActionMode(this)
            true
        }

    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }

    fun setData(favoriteEntityList: List<FavoriteEntity>) {
        val favoritesDiffUtil = RecipesDiffUtil(favoriteList, favoriteEntityList)
        val diffUtilResult = DiffUtil.calculateDiff(favoritesDiffUtil)
        favoriteList = favoriteEntityList
        diffUtilResult.dispatchUpdatesTo(this)
    }

    override fun onCreateActionMode(action: ActionMode?, menu: Menu?): Boolean {
        action?.menuInflater?.inflate(R.menu.favorite_contextual_menu, menu)
        return true
    }

    override fun onPrepareActionMode(action: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(action: ActionMode?, menu: MenuItem?): Boolean {
        return true
    }

    override fun onDestroyActionMode(action: ActionMode?) {
        
    }
}