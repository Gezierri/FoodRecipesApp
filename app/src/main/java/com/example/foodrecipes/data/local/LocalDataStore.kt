package com.example.foodrecipes.data.local

import com.example.foodrecipes.data.database.RecipesDao
import com.example.foodrecipes.data.database.entities.FavoriteEntity
import com.example.foodrecipes.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataStore @Inject constructor(
    private val recipesDao: RecipesDao
) {

    fun readRecipes(): Flow<List<RecipesEntity>> = recipesDao.readRecipes()

    suspend fun insertRecipes(recipesEntity: RecipesEntity){
        recipesDao.insertRecipes(recipesEntity)
    }

    fun readFavorites(): Flow<List<FavoriteEntity>> = recipesDao.readFavorites()

    suspend fun insertFavorite(favoriteEntity: FavoriteEntity){
        recipesDao.insertFavorite(favoriteEntity)
    }

    suspend fun deleteFavorite(favoriteEntity: FavoriteEntity){
        recipesDao.deleteFavorite(favoriteEntity)
    }

    suspend fun deleteAllFavorites(){
        recipesDao.deleteAllFavorites()
    }
}