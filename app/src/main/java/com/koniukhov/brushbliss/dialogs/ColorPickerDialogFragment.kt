package com.koniukhov.brushbliss.dialogs

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.koniukhov.brushbliss.R
import com.koniukhov.brushbliss.databinding.FragmentDialogColorPickerBinding
import com.koniukhov.brushbliss.di.DispatcherMain
import com.koniukhov.brushbliss.util.BLUE_TAG
import com.koniukhov.brushbliss.util.DEFAULT_COLOR
import com.koniukhov.brushbliss.util.GREEN_TAG
import com.koniukhov.brushbliss.util.HexInputFilter
import com.koniukhov.brushbliss.util.RED_TAG
import com.koniukhov.brushbliss.util.Util
import com.koniukhov.brushbliss.viewmodels.UserSettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ColorPickerDialogFragment : DialogFragment() {
    private var _binding: FragmentDialogColorPickerBinding? = null
    private val binding get() = _binding!!
    private val userSettingsViewModel: UserSettingsViewModel by activityViewModels()
    @DispatcherMain
    @Inject lateinit var dispatcherMain: CoroutineDispatcher

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentDialogColorPickerBinding.inflate(LayoutInflater.from(requireContext()))

        collectUserSettings()
        setSlidersObservers()
        addEditTextInputFilter()
        setEditTextObserver()

        return createDialog()
    }

    private fun collectUserSettings() {
        lifecycleScope.launch(dispatcherMain) {
            userSettingsViewModel.userSettingsFlow.collect {
                setColorViewBackground(it.color)
                initSlidersValues(it.color.red, it.color.green, it.color.blue)
            }
        }
    }

    private fun createDialog(): AlertDialog {
        return MaterialAlertDialogBuilder(requireContext()).apply {
            setView(binding.root)
            setTitle(R.string.dialog_color_picker_title)
            setNegativeButton(R.string.close){ _, _ ->
                dismiss()
            }
            setPositiveButton(R.string.save){ _, _ ->
                saveNewColor()
                dismiss()
            }
        }.create()
    }

    private fun saveNewColor() {
        var color = DEFAULT_COLOR
        val hexColor = binding.hexEdittext.text.toString()

        if (hexColor.isEmpty()) {
            color = Color.rgb(
                binding.redSlider.value.toInt(),
                binding.greenSlider.value.toInt(),
                binding.blueSlider.value.toInt()
            )
        } else {
            val rgb = Util.hexToRgb(hexColor)

            if (rgb != null) {
                color = rgb
            }
        }
        userSettingsViewModel.updateColor(color)
    }

    private fun setColorViewBackground(color: Int) {
        binding.colorView.setBackgroundColor(color)
    }

    private fun setSlidersObservers(){
        binding.redSlider.addOnChangeListener { _, value, _ ->
            setSliderListener(value, RED_TAG)
        }

        binding.greenSlider.addOnChangeListener { _, value, _ ->
            setSliderListener(value, GREEN_TAG)
        }

        binding.blueSlider.addOnChangeListener { _, value, _ ->
            setSliderListener(value, BLUE_TAG)
        }
    }

    private fun addEditTextInputFilter(){
        binding.hexEdittext.filters = arrayOf(HexInputFilter())
    }

    private fun setEditTextObserver(){
        binding.hexEdittext.doAfterTextChanged {
            val color = Util.hexToRgb(it.toString())
            if (color != null){
                setColorViewBackground(color)
            }
        }
    }

    private fun setSliderListener(value: Float, colorType: String){
        val red = if (colorType == RED_TAG) value.toInt() else binding.redSlider.value.toInt()
        val green = if (colorType == GREEN_TAG) value.toInt() else binding.greenSlider.value.toInt()
        val blue = if (colorType == BLUE_TAG) value.toInt() else binding.blueSlider.value.toInt()
        val color = Color.rgb(red, green, blue)

        setColorViewBackground(color)
    }

    private fun initSlidersValues(red: Int, green: Int, blue: Int){
        binding.redSlider.value = red.toFloat()
        binding.greenSlider.value = green.toFloat()
        binding.blueSlider.value = blue.toFloat()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}