package com.koniukhov.brushbliss.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.koniukhov.brushbliss.databinding.FragmentDrawingBinding
import com.koniukhov.brushbliss.util.DRAWING_BOTTOM_SHEET_TAG

class DrawingFragment : Fragment() {
    private var _binding: FragmentDrawingBinding? = null
    private val binding get() = _binding!!
    private lateinit var modalBottomSheet: DrawingBottomSheet

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDrawingBinding.inflate(inflater, container, false)

        initClickListeners()

        return binding.root
    }

    private fun initClickListeners(){
        binding.bottomSheetBtn.setOnClickListener{
            modalBottomSheet = DrawingBottomSheet()
            modalBottomSheet.show(childFragmentManager, DRAWING_BOTTOM_SHEET_TAG)
        }
    }
}