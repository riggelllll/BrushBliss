package com.koniukhov.brushbliss.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.koniukhov.brushbliss.databinding.DrawingBottomSheetContentBinding

class DrawingBottomSheet : BottomSheetDialogFragment() {
    private var _binding: DrawingBottomSheetContentBinding? = null
    private val  binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding =  DrawingBottomSheetContentBinding.inflate(inflater, container, false)
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

    companion object {
        const val TAG = "DrawingBottomSheet"
    }
}