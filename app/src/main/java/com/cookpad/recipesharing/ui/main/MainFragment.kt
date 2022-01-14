package com.cookpad.recipesharing.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cookpad.recipesharing.databinding.MainActivityBinding

class MainFragment : Fragment() {

    private val mainViewModel by viewModels<MainViewModel>()

    private var _binding: MainActivityBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainActivityBinding.inflate(inflater, container, false)
        return binding.root
    }


}