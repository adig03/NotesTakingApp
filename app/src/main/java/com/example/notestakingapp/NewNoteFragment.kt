package com.example.notestakingapp

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.notestakingapp.databinding.FragmentNewNoteBinding
import com.example.notestakingapp.room.Note
import com.example.notestakingapp.viewModels.NoteViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class NewNoteFragment : Fragment() {

    // Binding object to access the layout elements
    private lateinit var binding: FragmentNewNoteBinding

    // View object for interacting with the UI
    private lateinit var mView: View

    // ViewModel to interact with the notes data
    private lateinit var notesViewModel: NoteViewModel

    // For storing the current date and time
    private lateinit var currentDateTime: LocalDateTime

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Fragment initialization logic can be placed here if needed
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using View Binding
        binding = FragmentNewNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Ensure that this method is only called on Android API 26 and above due to LocalDateTime
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the ViewModel from the parent Activity (MainActivity)
        notesViewModel = (activity as MainActivity).notesViewModel

        // Store the view for future reference (e.g., for showing Toasts)
        mView = view

        // Set up the FloatingActionButton click listener to save the note
        binding.floatingButtonToSave.setOnClickListener {
            saveNote() // Save note when FAB is clicked
        }
    }

    // This method saves the note entered by the user
    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveNote() {
        // Get the title and content from EditText fields
        val noteTitle = binding.newNoteTitle.text.toString()
        val noteBody = binding.newNoteContent.text.toString()

        // Get the current date and time, and format it
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val formattedDateTime = currentDateTime.format(formatter)

        // Check if the title is not empty before saving the note
        if (noteTitle.isNotEmpty()) {
            // Create a new Note object with the title, body, and formatted timestamp
            val note = Note(0, noteTitle, noteBody, formattedDateTime.toString())

            // Add the note to the ViewModel, which will handle saving it to the database
            notesViewModel.addNote(note)

            // Show a confirmation Toast message
            Toast.makeText(mView.context, "Note saved successfully", Toast.LENGTH_SHORT).show()

            // Navigate back to the HomeFragment after saving
            view?.findNavController()?.navigate(R.id.action_newNoteFragment_to_homeFragment)
        } else {
            // Show an error Toast message if the title is empty
            Toast.makeText(mView.context, "Please Enter Note Title", Toast.LENGTH_SHORT).show()
        }
    }
}

