package com.example.personalnotesapp.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.personalnotesapp.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.settings.observe(viewLifecycleOwner) { settings ->
            binding.darkModeSwitch.isChecked = settings.isDarkMode
            binding.autoSaveSwitch.isChecked = settings.autosave
            binding.fontSizeSeekBar.progress = settings.fontSize
        }

        binding.darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateSettings(
                isDarkMode = isChecked,
                fontSize = binding.fontSizeSeekBar.progress,
                autosave = binding.autoSaveSwitch.isChecked
            )
        }
        binding.autoSaveSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateSettings(
                isDarkMode = binding.darkModeSwitch.isChecked,
                fontSize = binding.fontSizeSeekBar.progress,
                autosave = isChecked
            )
        }

        binding.fontSizeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.updateSettings(
                    isDarkMode = binding.darkModeSwitch.isChecked,
                    fontSize = progress,
                    autosave = binding.autoSaveSwitch.isChecked
                )
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        binding.exportButton.setOnClickListener {
            // TODO: implement export
        }

        binding.backupButton.setOnClickListener {
            // TODO: implement backup
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
