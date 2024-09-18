package com.example.notestakingapp.viewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notestakingapp.Repository.NoteRepository

class NoteViewModelFactory(
    private val app: Application,
    private val repository: NoteRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Check if the requested ViewModel is of the type NoteViewModel
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            // Return a new instance of NoteViewModel
            return NoteViewModel(app, repository) as T
        }
        // Throw exception if the ViewModel is not of the expected type
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
