package com.example.foodrecipes.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodrecipes.model.Result
import com.example.foodrecipes.utils.Constants.Companion.FAVORITE_TABLE

@Entity(tableName = FAVORITE_TABLE)
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: Result
)