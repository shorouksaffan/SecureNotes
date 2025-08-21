package com.example.personalnotesapp.presentation.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.personalnotesapp.databinding.ItemNoteBinding
import com.example.personalnotesapp.domain.model.Note
import com.example.personalnotesapp.utils.FontSize

class NotesAdapter(
    private val onNoteClick: (Note) -> Unit
) : ListAdapter<Note, NotesAdapter.NoteViewHolder>(DiffCallback) {

    object DiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Note, newItem: Note) = oldItem == newItem
    }

    private var fontSizeBaseSp: Int = 16

    fun setFontBase(fontSp: Int) {
        fontSizeBaseSp = fontSp
        notifyDataSetChanged()
    }

    inner class NoteViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note) {
            binding.noteTitle.text = note.title
            binding.noteContent.text = note.content
            binding.root.setOnClickListener { onNoteClick(note) }
            FontSize.applyFontSizeToViews(binding.root, fontSizeBaseSp)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
