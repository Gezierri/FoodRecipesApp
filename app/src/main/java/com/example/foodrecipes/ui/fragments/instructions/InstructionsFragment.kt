package com.example.foodrecipes.ui.fragments.instructions

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.example.foodrecipes.R
import com.example.foodrecipes.databinding.FragmentInstructionsBinding
import com.example.foodrecipes.model.Result
import com.example.foodrecipes.utils.Constants.Companion.RECIPES_BUNDLE

class InstructionsFragment : Fragment() {

    private lateinit var binding: FragmentInstructionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInstructionsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myBundle: Result? = arguments?.getParcelable(RECIPES_BUNDLE)
        binding.instructionWebView.webViewClient = object : WebViewClient(){}
        val websiteUrl = myBundle?.sourceUrl
        binding.instructionWebView.loadUrl(websiteUrl.toString())
    }

}