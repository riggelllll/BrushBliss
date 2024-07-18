package com.koniukhov.brushbliss.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.koniukhov.brushbliss.R
import com.koniukhov.brushbliss.databinding.FragmentHomeBinding

class HomeFragment : Fragment(){

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.fragment = this
        return binding.root
    }

    fun navigateToDrawingFragment() {
        findNavController().navigate(R.id.drawingFragment)
    }
}