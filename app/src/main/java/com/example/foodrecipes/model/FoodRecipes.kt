package com.example.foodrecipes.model

import com.google.gson.annotations.SerializedName

data class FoodRecipes(
    @SerializedName("results")
    val results: List<Result>
)
