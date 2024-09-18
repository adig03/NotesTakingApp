package com.example.notestakingapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notestakingapp.Adapter.NotesAdapter
import com.example.notestakingapp.databinding.FragmentHomeBinding
import com.example.notestakingapp.room.Note
import com.example.notestakingapp.viewModels.NoteViewModel
import androidx.navigation.fragment.findNavController

// HomeFragment displays a list of notes and handles note-related actions
class HomeFragment : Fragment(R.layout.fragment_home),
    androidx.appcompat.widget.SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentHomeBinding  // View binding object for fragment_home layout
    private lateinit var noteAdapter: NotesAdapter  // Adapter to handle note items in the RecyclerView
    private lateinit var notesViewModel: NoteViewModel  // ViewModel for managing UI-related data

    // onCreate: Fragment initialization logic
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // onCreateView: Inflate the layout and set up data binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root  // Return the root view of the layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notesViewModel = (activity as MainActivity).notesViewModel

        // Initialize the adapter
        noteAdapter = NotesAdapter()

        // Set up RecyclerView for displaying notes
        setUpRecyclerView()

        // Set up SearchBar to handle text input for searching notes
        binding.searchBar.setOnQueryTextListener(this)

        // Handle Floating Action Button click to navigate to add a new note
        binding.fbForAddNewNote.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_newNoteFragment)
        }
    }


    // onQueryTextSubmit: Handles the action when the user submits a search query
    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            // Observe the search result and update the list of notes
            notesViewModel.searchNote(it).observe(viewLifecycleOwner, { notes ->
                noteAdapter.differ.submitList(notes)  // Submit the filtered list to the adapter
                updateUI(notes)  // Update UI based on whether there are notes to display
            })
        }
        return true  // Return true to indicate the query has been handled
    }

    // onQueryTextChange: Handles changes in the search query as the user types
    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let {
            // Observe the search result and update the list of notes in real-time
            notesViewModel.searchNote(it).observe(viewLifecycleOwner, { notes ->
                noteAdapter.differ.submitList(notes)  // Update the list with the filtered notes
                updateUI(notes)  // Update UI to show or hide the notes/recycler view
            })
        }
        return true  // Return true to indicate the change has been handled
    }

    // setUpRecyclerView: Initializes the RecyclerView with an adapter and layout manager
    private fun setUpRecyclerView() {
        binding.recyView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = noteAdapter
        }

        notesViewModel.getAllNotes().observe(viewLifecycleOwner, { notes ->
            noteAdapter.differ.submitList(notes)
            updateUI(notes)
        })
    }


    // updateUI: Toggles visibility of the RecyclerView or 'No Notes' message based on the notes list
    private fun updateUI(notes: List<Note>?) {
        if (notes != null && notes.isNotEmpty()) {
            // If there are notes, display the RecyclerView and hide the 'No Notes' message
            binding.recyView.visibility = View.VISIBLE
        } else {
            // If no notes, hide the RecyclerView and show the 'No Notes' message
            binding.recyView.visibility = View.GONE
        }
    }
}
