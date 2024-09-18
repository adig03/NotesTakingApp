package com.example.notestakingapp.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notestakingapp.HomeFragmentDirections
import com.example.notestakingapp.databinding.NotesItemBinding
import com.example.notestakingapp.room.Note


class NotesAdapter: RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    class NoteViewHolder(val itemBinding: NotesItemBinding) : RecyclerView.ViewHolder(itemBinding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.note_id == newItem.note_id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NotesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = differ.currentList[position]
        holder.itemBinding.noteName.text = currentNote.note_title
        holder.itemBinding.noteDesc.text = currentNote.note_desc

        holder.itemView.setOnClickListener {
        // Use the callback to handle item clicks
            val direction = HomeFragmentDirections.actionHomeFragmentToUpdateNoteFragment(currentNote)
            Navigation.findNavController(it).navigate(direction)
        }
    }
}
