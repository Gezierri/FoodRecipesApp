package com.example.foodrecipes.data.remote

import com.example.foodrecipes.data.network.FoodRecipesApi
import com.example.foodrecipes.model.FoodRecipes
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi
) {

    suspend fun getRecipesAllRecipes(queries: Map<String, String>): Response<FoodRecipes> =
        foodRecipesApi.getAllRecipes(queries)

    suspend fun searchRecipes(queries: Map<String, String>): Response<FoodRecipes> =
        foodRecipesApi.searchRecipes(queries)
}