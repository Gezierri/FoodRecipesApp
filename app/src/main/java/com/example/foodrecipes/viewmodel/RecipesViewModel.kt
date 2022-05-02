package com.example.foodrecipes.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodrecipes.BuildConfig.API_KEY
import com.example.foodrecipes.data.local.DataStoreRepository
import com.example.foodrecipes.utils.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.foodrecipes.utils.Constants.Companion.DEFAULT_MEAL_TYPE
import com.example.foodrecipes.utils.Constants.Companion.DEFAULT_RECIPES_NUMBER
import com.example.foodrecipes.utils.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.example.foodrecipes.utils.Constants.Companion.QUERY_API_KEY
import com.example.foodrecipes.utils.Constants.Companion.QUERY_DIET
import com.example.foodrecipes.utils.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.example.foodrecipes.utils.Constants.Companion.QUERY_NUMBER
import com.example.foodrecipes.utils.Constants.Companion.QUERY_SEARCH
import com.example.foodrecipes.utils.Constants.Companion.QUERY_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {

    val readMealAndDietType = dataStoreRepository.readMealAndDietType
    val readBackOnline = dataStoreRepository.readBackOnline.asLiveData()

    var networkStatus = false
    var backOnline = false

    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

    fun saveMealAndDiet(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveMealAndDietType(mealType, mealTypeId, dietType, dietTypeId)
        }

    fun saveBackOnline(backOnline: Boolean){
        viewModelScope.launch(Dispatchers.IO){
            dataStoreRepository.saveBackOnline(backOnline)
        }
    }

    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        viewModelScope.launch {
            readMealAndDietType.collect {
                mealType = it.selectedMealType
                dietType = it.selectedDietType
            }
        }

        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = mealType
        queries[QUERY_DIET] = dietType
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }

    fun applySearchQueries(searchQuery: String): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        viewModelScope.launch {
            readMealAndDietType.collect {
                mealType = it.selectedMealType
                dietType = it.selectedDietType
            }
        }


        queries[QUERY_SEARCH] = searchQuery
        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = mealType
        queries[QUERY_DIET] = dietType
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }

    fun showNetworkStatus() {
        if (!networkStatus) {
            Toast.makeText(getApplication(), "No Internet Connection", Toast.LENGTH_SHORT).show()
            saveBackOnline(true)
        }else if(networkStatus){
            if (backOnline){
                Toast.makeText(getApplication(), "We are back Connection", Toast.LENGTH_SHORT).show()
            }
        }
    }
}