package com.example.foodrecipes.data.local

import com.example.foodrecipes.data.database.RecipesDao
import com.example.foodrecipes.data.database.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataStore @Inject constructor(
    private val recipesDao: RecipesDao
) {

    fun readDatabase(): Flow<List<RecipesEntity>> = recipesDao.readRecipes()

    suspend fun insertRecipes(recipesEntity: RecipesEntity){
        recipesDao.insertRecipes(recipesEntity)
    }
}