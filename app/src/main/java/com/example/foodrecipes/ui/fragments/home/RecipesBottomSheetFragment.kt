package com.example.foodrecipes.ui.fragments.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.foodrecipes.R
import com.example.foodrecipes.databinding.FragmentRecipesBottomSheetBinding
import com.example.foodrecipes.utils.Constants
import com.example.foodrecipes.utils.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.foodrecipes.utils.Constants.Companion.DEFAULT_MEAL_TYPE
import com.example.foodrecipes.viewmodel.MainViewModel
import com.example.foodrecipes.viewmodel.RecipesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.util.*

class RecipesBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var viewModel: RecipesViewModel
    private lateinit var binding: FragmentRecipesBottomSheetBinding

    private var mealTypeChip = DEFAULT_MEAL_TYPE
    private var mealTypeIdChip = 0
    private var dietTypeChip = DEFAULT_DIET_TYPE
    private var dietTypeIdChip = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecipesBottomSheetBinding.inflate(inflater)

        viewModel.readMealAndDietType.asLiveData().observe(viewLifecycleOwner) { mealAndDiet ->
            mealTypeChip = mealAndDiet.selectedMealType
            dietTypeChip = mealAndDiet.selectedDietType
            updateChip(mealAndDiet.selectedMealTypeId, binding.mealTypeChipGroup)
            updateChip(mealAndDiet.selectedDietTypeId, binding.dietTypeChipGroup)
        }

        binding.mealTypeChipGroup.setOnCheckedChangeListener { group, selectChipId ->
            val chip = group.findViewById<Chip>(selectChipId)
            val selectedMealType = chip.text.toString().lowercase(Locale.getDefault())
            mealTypeChip = selectedMealType
            mealTypeIdChip = selectChipId
        }

        binding.dietTypeChipGroup.setOnCheckedChangeListener { group, selectChipId ->
            val chip = group.findViewById<Chip>(selectChipId)
            val selectedDietType = chip.text.toString().lowercase(Locale.getDefault())
            dietTypeChip = selectedDietType
            dietTypeIdChip = selectChipId
        }

        binding.applyBtn.setOnClickListener {
            viewModel.saveMealAndDiet(
                mealTypeChip,
                mealTypeIdChip,
                dietTypeChip,
                dietTypeIdChip
            )
            val action = RecipesBottomSheetFragmentDirections.actionRecipesBottomSheetFragmentToHomeFragment(true)
            findNavController().navigate(action)
        }
        return binding.root
    }

    private fun updateChip(chipId: Int, chipGroup: ChipGroup) {
        if (chipId != 0) {
            try {
                chipGroup.findViewById<Chip>(chipId).isChecked = true
            } catch (e: Exception) {
                Log.d("RecipesBottomSheet", e.message.toString())
            }
        }
    }
}