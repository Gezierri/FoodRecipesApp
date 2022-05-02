package com.example.foodrecipes.bidingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.foodrecipes.data.database.RecipesEntity
import com.example.foodrecipes.model.FoodRecipes
import com.example.foodrecipes.utils.NetworkResult

class RecipesBinding {

    companion object {

        @BindingAdapter("readApiResponse", "readDatabse", requireAll = true)
        @JvmStatic
        fun errorImageViewVisibility(
            imageView: ImageView,
            apiResponse: NetworkResult<FoodRecipes>?,
            database: List<RecipesEntity>?
        ) {
            if (apiResponse is NetworkResult.Error && database.isNullOrEmpty()) {
                imageView.visibility = View.VISIBLE
            } else if (apiResponse is NetworkResult.Loading) {
                imageView.visibility = View.INVISIBLE
            }else if (apiResponse is NetworkResult.Success){
                imageView.visibility = View.INVISIBLE
            }
        }

        @BindingAdapter("readApiResponse2", "readDatabse2", requireAll = true)
        @JvmStatic
        fun errorTextViewVisibility(
            textView: TextView,
            apiResponse: NetworkResult<FoodRecipes>?,
            database: List<RecipesEntity>?
        ) {
            if (apiResponse is NetworkResult.Error && database.isNullOrEmpty()) {
                textView.visibility = View.VISIBLE
            } else if (apiResponse is NetworkResult.Loading) {
                textView.visibility = View.INVISIBLE
            }else if (apiResponse is NetworkResult.Success){
                textView.visibility = View.INVISIBLE
            }
        }
    }
}