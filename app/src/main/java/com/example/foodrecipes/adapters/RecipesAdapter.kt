package com.example.foodrecipes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipes.databinding.RecipesRowBinding
import com.example.foodrecipes.model.FoodRecipes
import com.example.foodrecipes.model.Result
import java.util.zip.Inflater

class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.MyViewHolder>() {

    private var recipesList = emptyList<Result>()

    class MyViewHolder(private val binding: RecipesRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result) {
            binding.result = result
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecipesRowBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentResult = recipesList[position]
        holder.bind(currentResult)
    }

    override fun getItemCount(): Int {
        return recipesList.size
    }

    fun setData(newData: FoodRecipes) {
        val recipesDiffUtil = RecipesDiffUtil(recipesList, newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipesList = newData.results
        diffUtilResult.dispatchUpdatesTo(this)
    }
}