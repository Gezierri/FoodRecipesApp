package com.example.foodrecipes.bidingadapters

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.example.foodrecipes.R
import com.example.foodrecipes.model.FoodRecipes
import com.example.foodrecipes.model.Result
import com.example.foodrecipes.ui.fragments.home.HomeFragmentDirections
import java.lang.Exception

class RecipesRowBinding {

    companion object {

        @BindingAdapter("onClickRowRecipes")
        @JvmStatic
        fun onClickRowRecipes(rowRecipes: ConstraintLayout, result: Result){
            rowRecipes.setOnClickListener {
                try {
                    val action = HomeFragmentDirections.actionHomeFragmentToDetailsActivity(result)
                    rowRecipes.findNavController().navigate(action)
                }catch (e: Exception){
                    Log.e("onRecipesClickListener", e.toString())
                }
            }
        }

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageUrl: String) {
            imageView.load(imageUrl) {
                crossfade(600)
                error(R.drawable.place_holder_error)
            }
        }

        @BindingAdapter("setNumberOfLikes")
        @JvmStatic
        fun setNumberOfLikes(textView: TextView, likes: Int) {
            textView.text = likes.toString()
        }

        @BindingAdapter("setNumberOfMinutes")
        @JvmStatic
        fun setNumberOfMinutes(textView: TextView, minutes: Int) {
            textView.text = minutes.toString()
        }

        @BindingAdapter("applyVeganColor")
        @JvmStatic
        fun applyVeganColor(view: View, vegan: Boolean) {
            if (vegan) {
                when (view) {
                    is TextView -> {
                        view.setTextColor(
                            ContextCompat.getColor(
                                view.context,
                                R.color.green
                            )
                        )
                    }
                    is ImageView -> {
                        view.setColorFilter(
                            ContextCompat.getColor(
                                view.context,
                                R.color.green
                            )
                        )
                    }
                }
            }
        }
    }
}