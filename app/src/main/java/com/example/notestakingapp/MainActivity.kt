package com.example.notestakingapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.room.Room
import com.example.notestakingapp.databinding.ActivityMainBinding
import com.example.notestakingapp.viewModels.NoteViewModel
import com.example.notestakingapp.room.NoteDB
import com.example.notestakingapp.Repository.NoteRepository
import com.example.notestakingapp.room.Note
import com.example.notestakingapp.viewModels.NoteViewModelFactory
import com.example.notestakingapp.room.NotesDAO

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var notesViewModel: NoteViewModel
    private lateinit var navController: NavController
    lateinit var db: NoteDB
    lateinit var dao: NotesDAO


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the activity layout using View Binding

        db =    NoteDB.getInstance(applicationContext)
        dao =   db.getNotesDao()
        dao.getAllNotesInDB()


        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Set up ViewModel
        setUpViewModel()
    }

    private fun setUpViewModel() {
        // Create an instance of the Room database
        val notesDatabase = Room.databaseBuilder(
            applicationContext,  // Use application context
            NoteDB::class.java,   // The NoteDB class
            "notes_database"      // The name of the database
        ).build()

        // Create an instance of the repository
        val noteRepository = NoteRepository(notesDatabase)

        // Initialize the ViewModel with the repository
        val viewModelFactory = NoteViewModelFactory(application, noteRepository)

        // Initialize the ViewModel using ViewModelProvider
        notesViewModel = ViewModelProvider(this, viewModelFactory)[NoteViewModel::class.java]
    }
}
