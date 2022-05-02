package com.example.foodrecipes.ui.fragments.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodrecipes.R
import com.example.foodrecipes.adapters.FavoriteRecipesAdapter
import com.example.foodrecipes.databinding.FavoriteRecipesRowLayoutBinding
import com.example.foodrecipes.databinding.FragmentFavoriteBinding
import com.example.foodrecipes.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private val  mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentFavoriteBinding
    private val adapter: FavoriteRecipesAdapter by lazy { FavoriteRecipesAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        mainViewModel.readFavorites.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()){
                adapter.setData(it)
                hideNoMessageImages()
            }

        }
    }

    private fun setRecyclerView(){
        binding.rvFavorite.adapter = adapter
        binding.rvFavorite.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun hideNoMessageImages(){
        binding.ivNoFavorites.visibility = View.INVISIBLE
        binding.tvNoFavorites.visibility = View.INVISIBLE
    }
}