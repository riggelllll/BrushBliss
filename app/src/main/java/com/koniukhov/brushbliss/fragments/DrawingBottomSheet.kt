package com.koniukhov.brushbliss.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.koniukhov.brushbliss.databinding.DrawingBottomSheetContentBinding
import com.koniukhov.brushbliss.dialogs.BaseBrushDialogFragment
import com.koniukhov.brushbliss.util.BASE_BRUSH_DIALOG_TAG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DrawingBottomSheet : BottomSheetDialogFragment() {
    private var _binding: DrawingBottomSheetContentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding =  DrawingBottomSheetContentBinding.inflate(inflater, container, false)
        initClickListeners()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        setDialogBehaviour()
    }

    private fun setDialogBehaviour(){
        dialog?.let {
            val modalBottomSheetBehavior = (it as BottomSheetDialog).behavior
            modalBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    private fun initClickListeners(){
        binding.baseBrush.setOnClickListener{
            val dialog = BaseBrushDialogFragment()
            dialog.show(parentFragmentManager, BASE_BRUSH_DIALOG_TAG)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}