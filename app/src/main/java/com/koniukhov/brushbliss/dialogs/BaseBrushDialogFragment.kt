package com.koniukhov.brushbliss.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.koniukhov.brushbliss.R
import com.koniukhov.brushbliss.databinding.FragmentDialogBaseBrushBinding
import com.koniukhov.brushbliss.di.DispatcherMain
import com.koniukhov.brushbliss.viewmodels.UserSettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BaseBrushDialogFragment : DialogFragment() {
    private var _binding: FragmentDialogBaseBrushBinding? = null
    private val binding get() = _binding!!
    private val userSettingsViewModel: UserSettingsViewModel by activityViewModels()
    @DispatcherMain
    @Inject lateinit var dispatcherMain: CoroutineDispatcher

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentDialogBaseBrushBinding.inflate(LayoutInflater.from(requireContext()))

        collectUserSettings()

        return createDialog()
    }

    private fun collectUserSettings() {
        lifecycleScope.launch(dispatcherMain) {
            userSettingsViewModel.userSettingsFlow.collect {
                binding.strokeWidthSlider.value = it.strokeWidth
                binding.alphaSlider.value = it.alpha.toFloat()
            }
        }
    }

    private fun createDialog(): AlertDialog{
        return MaterialAlertDialogBuilder(requireContext()).apply {
            setView(binding.root)
            setTitle(R.string.dialog_base_brush_title)
            setNegativeButton(R.string.close){_, _ ->
                dismiss()
            }
            setPositiveButton(R.string.save){_, _ ->
                userSettingsViewModel.updateStrokeWidth(binding.strokeWidthSlider.value)
                userSettingsViewModel.updateAlpha(binding.alphaSlider.value.toInt())
                dismiss()
            }
        }.create()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}