package com.example.personalnotesapp.presentation.settings

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.personalnotesapp.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    companion object {
        private const val REQUEST_CODE_CREATE_FILE = 100
    }

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
            binding.autoSaveSwitch.isChecked = settings.autoSave
            binding.fontSizeSeekBar.progress = settings.fontSize

            // ✅ Apply theme when restoring settings
            AppCompatDelegate.setDefaultNightMode(
                if (settings.isDarkMode) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        binding.darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateSettings(
                isDarkMode = isChecked,
                fontSize = binding.fontSizeSeekBar.progress,
                autosave = binding.autoSaveSwitch.isChecked
            )

            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        binding.autoSaveSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateSettings(
                isDarkMode = binding.darkModeSwitch.isChecked,
                fontSize = binding.fontSizeSeekBar.progress,
                autosave = isChecked
            )
        }

        binding.fontSizeSeekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
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
            val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "text/plain"
                putExtra(Intent.EXTRA_TITLE, "all_notes.txt")
            }
            startActivityForResult(intent, REQUEST_CODE_CREATE_FILE)
        }

        binding.fabSettings.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CREATE_FILE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                viewModel.getAllNotesForExport { notesText ->
                    requireContext().contentResolver.openOutputStream(uri)?.use { output ->
                        output.write(notesText.toByteArray())
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
