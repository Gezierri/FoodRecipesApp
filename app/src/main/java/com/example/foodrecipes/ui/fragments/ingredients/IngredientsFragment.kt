package com.example.foodrecipes.ui.fragments.ingredients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodrecipes.R
import com.example.foodrecipes.adapters.IngredientsAdapter
import com.example.foodrecipes.databinding.FragmentIngredientsBinding
import com.example.foodrecipes.databinding.IngredientsRowBinding
import com.example.foodrecipes.model.Result
import com.example.foodrecipes.utils.Constants.Companion.RECIPES_BUNDLE

class IngredientsFragment : Fragment() {

    private val ingredientsAdapter: IngredientsAdapter by lazy { IngredientsAdapter() }
    private lateinit var binding: FragmentIngredientsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIngredientsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        val ingredientsBundle: Result? = args?.getParcelable(RECIPES_BUNDLE)

        setupRecyclerView()

        ingredientsBundle?.extendedIngredients?.let {  ingredientsAdapter.setData(it)}
    }

    private fun setupRecyclerView(){
        binding.rvIngredients.adapter = ingredientsAdapter
        binding.rvIngredients.layoutManager = LinearLayoutManager(requireContext())
    }
}