package com.example.foodrecipes.data.local

import com.example.foodrecipes.data.database.RecipesDao
import com.example.foodrecipes.data.database.entities.FavoriteEntity
import com.example.foodrecipes.data.database.entities.FoodJokeEntity
import com.example.foodrecipes.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataStore @Inject constructor(
    private val recipesDao: RecipesDao
) {

    suspend fun insertRecipes(recipesEntity: RecipesEntity){
        recipesDao.insertRecipes(recipesEntity)
    }

    suspend fun insertFavorite(favoriteEntity: FavoriteEntity){
        recipesDao.insertFavorite(favoriteEntity)
    }

    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity){
        recipesDao.insertFoodJoke(foodJokeEntity)
    }

    fun readRecipes(): Flow<List<RecipesEntity>> = recipesDao.readRecipes()

    fun readFavorites(): Flow<List<FavoriteEntity>> = recipesDao.readFavorites()

    fun readFoodJoke(): Flow<List<FoodJokeEntity>> = recipesDao.readFoodJoke()

    suspend fun deleteFavorite(favoriteEntity: FavoriteEntity){
        recipesDao.deleteFavorite(favoriteEntity)
    }

    suspend fun deleteAllFavorites(){
        recipesDao.deleteAllFavorites()
    }
}