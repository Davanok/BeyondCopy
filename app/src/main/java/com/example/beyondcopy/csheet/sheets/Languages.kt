package com.example.beyondcopy.csheet.sheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.beyondcopy.R
import com.example.beyondcopy.databinding.FragmentLanguagesBinding

class Languages : Fragment() {
    private lateinit var binding: FragmentLanguagesBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLanguagesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.languagesListTextView.text = requireArguments().getString("languages")?: getString(R.string.languages)
    }
}