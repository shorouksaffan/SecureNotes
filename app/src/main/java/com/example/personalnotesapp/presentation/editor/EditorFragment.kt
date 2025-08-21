package com.example.personalnotesapp.presentation.editor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.personalnotesapp.databinding.FragmentEditorBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditorFragment : Fragment() {

    private var _binding: FragmentEditorBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EditorViewModel by viewModels()

    private var noteId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteId = arguments?.getInt("noteId", 0) ?: 0

        if (noteId != 0) {
            viewModel.loadNote(noteId)
            binding.deleteButton.visibility = View.VISIBLE
        }


        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()
            viewModel.saveNote(noteId, title, content)
            findNavController().navigateUp()
        }
        binding.deleteButton.setOnClickListener {
            if (noteId != 0) {
                viewModel.deleteNote(noteId)
            }
            findNavController().navigateUp()
        }

        viewModel.note.observe(viewLifecycleOwner) { note ->
            binding.titleEditText.setText(note?.title ?: "")
            binding.contentEditText.setText(note?.content ?: "")
            binding.privateCheckBox.isChecked = note?.isPrivate ?: false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
