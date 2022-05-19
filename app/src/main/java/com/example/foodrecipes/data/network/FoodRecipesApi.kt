package com.example.foodrecipes.data.network

import com.example.foodrecipes.model.FoodJoke
import com.example.foodrecipes.model.FoodRecipes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface FoodRecipesApi {

    @GET("/recipes/complexSearch")
    suspend fun getAllRecipes(
        @QueryMap
        queries: Map<String, String>
    ): Response<FoodRecipes>

    @GET("/recipes/complexSearch")
    suspend fun searchRecipes(
        @QueryMap
        queries: Map<String, String>
    ): Response<FoodRecipes>

    @GET("/food/jokes/random")
    suspend fun getFoodJoke(
        @Query("apiKey")
        apiKey: String
    ): Response<FoodJoke>
}