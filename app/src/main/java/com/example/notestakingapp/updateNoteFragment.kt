package com.example.notestakingapp

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notestakingapp.databinding.FragmentUpdateNoteBinding
import com.example.notestakingapp.viewModels.NoteViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Suppress("DEPRECATION")
class UpdateNoteFragment : Fragment(R.layout.fragment_update_note) {
    private lateinit var binding: FragmentUpdateNoteBinding
    private lateinit var notesViewModel: NoteViewModel
    private lateinit var nView: View

    private val args: UpdateNoteFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      setHasOptionsMenu(true) // This enables the options menu to be displayed
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentUpdateNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesViewModel = (activity as MainActivity).notesViewModel
        nView = view
        val currentNote = args.note

        // Set note data to the views
        binding.updateNoteTitle.setText(currentNote.note_title)
        binding.UpdateNoteBody.setText(currentNote.note_desc)
        binding.updateTimeStampDetails.text = currentNote.timestamp

        binding.fabForUpdate.setOnClickListener {
            updateChangesInNote()
        }
        binding.fabForDelete.setOnClickListener{
            deleteTheNote()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // No additional menu items needed
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId === android.R.id.home) {
            val backStackCount =
                requireFragmentManager().backStackEntryCount //check currently how many frags loaded
            if (backStackCount > 0) {
                requireFragmentManager().popBackStack() //go back to previously loaded fragment
            }
        }

        return super.onOptionsItemSelected(item)
    }



    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateChangesInNote() {
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val formattedDateTime = currentDateTime.format(formatter)

        val title = binding.updateNoteTitle.text.toString().trim()
        val body = binding.UpdateNoteBody.text.toString().trim()
        if (title.isNotEmpty()) {
            val updatedNote = args.note.copy(
                note_title = title,
                note_desc = body,
                timestamp = formattedDateTime
            )

            notesViewModel.updateNote(updatedNote)
            Toast.makeText(nView.context, "Note Updated Successfully", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateNoteFragment_to_homeFragment)
        } else {
            Toast.makeText(nView.context, "Please Enter Note Title", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteTheNote() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Delete Note")
            setMessage("Are you sure you want to delete this note?")

            setPositiveButton("Delete") { dialogInterface, _ ->
                notesViewModel.removeNote(args.note)
                Toast.makeText(requireContext(), "Note Deleted Successfully", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_updateNoteFragment_to_homeFragment)
                dialogInterface.dismiss()
            }

            setNegativeButton("Cancel") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }

            create()
            show()
        }
    }

}

