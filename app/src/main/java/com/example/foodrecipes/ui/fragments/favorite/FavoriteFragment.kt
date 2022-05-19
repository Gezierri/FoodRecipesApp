package com.example.foodrecipes.ui.fragments.favorite

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodrecipes.R
import com.example.foodrecipes.adapters.FavoriteRecipesAdapter
import com.example.foodrecipes.databinding.FragmentFavoriteBinding
import com.example.foodrecipes.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private val  mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentFavoriteBinding
    private val adapter: FavoriteRecipesAdapter by lazy { FavoriteRecipesAdapter(requireActivity(), mainViewModel) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setRecyclerView()
        mainViewModel.readFavorites.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()){
                adapter.setData(it)
                hideNoMessageImages()
            }else {
                binding.rvFavorite.visibility = View.INVISIBLE
                showNoMessageImages()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favorites_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteAll_favorites_recipes){
            mainViewModel.deleteAllFavorites()
            setSnackBar("All recipes removed.")
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setRecyclerView(){
        binding.rvFavorite.adapter = adapter
        binding.rvFavorite.layoutManager = LinearLayoutManager(requireContext())
    }


    private fun hideNoMessageImages(){
        binding.ivNoFavorites.visibility = View.INVISIBLE
        binding.tvNoFavorites.visibility = View.INVISIBLE
    }

    private fun showNoMessageImages(){
        binding.ivNoFavorites.visibility = View.VISIBLE
        binding.tvNoFavorites.visibility = View.VISIBLE
    }

    private fun setSnackBar(msg: String){
        Snackbar.make(
            binding.root,
            msg,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay"){}
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.clearContextualActionMode()
    }
}