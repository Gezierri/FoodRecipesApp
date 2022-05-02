package com.example.foodrecipes.ui.fragments.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import coil.load
import com.example.foodrecipes.R
import com.example.foodrecipes.databinding.FragmentHomeBinding
import com.example.foodrecipes.databinding.FragmentOverviewBinding
import com.example.foodrecipes.model.Result
import com.example.foodrecipes.utils.Constants.Companion.RECIPES_BUNDLE
import org.jsoup.Jsoup

class OverviewFragment : Fragment() {

    private lateinit var binding: FragmentOverviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOverviewBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        val bundleArgs: Result? = bundle?.getParcelable<Result>(RECIPES_BUNDLE)

        with(binding){
            bundleArgs?.let {
                mainImageView.load(it.image)
                titleTextView.text = it.title
                summaryTextView.text = Jsoup.parse(it.summary).text()
                timeTextView.text = it.readyInMinutes.toString()
                likesTextView.text = it.aggregateLikes.toString()

                updateColors(it.vegetarian, binding.vegetarianTextView, binding.vegetarianImageView)
                updateColors(it.vegan, binding.veganTextView, binding.veganImageView)
                updateColors(it.cheap, binding.cheapTextView, binding.cheapImageView)
                updateColors(it.dairyFree, binding.dairyFreeTextView, binding.dairyFreeImageView)
                updateColors(it.glutenFree, binding.glutenFreeTextView, binding.glutenFreeImageView)
                updateColors(it.veryHealthy, binding.healthyTextView, binding.healthyImageView)
            }
        }
    }

    private fun updateColors(stateIsOn: Boolean, textView: TextView, imageView: ImageView){
        if (stateIsOn){
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
        }
    }
}