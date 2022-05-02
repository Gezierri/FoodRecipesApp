package com.example.foodrecipes.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodrecipes.model.FoodRecipes
import com.example.foodrecipes.utils.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(var foodRecipes: FoodRecipes) {

    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}