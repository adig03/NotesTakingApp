package com.example.notestakingapp.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.query
import com.example.notestakingapp.Repository.NoteRepository
import com.example.notestakingapp.room.Note
import kotlinx.coroutines.launch

class NoteViewModel(app: Application, private val repository: NoteRepository ): AndroidViewModel(app){

    fun addNote(note: Note)=
        viewModelScope.launch{
           repository.insertNote(note)
        }
    fun removeNote(note: Note)=
        viewModelScope.launch{
            repository.deleteNote(note)
        }
    fun updateNote(note: Note)=
        viewModelScope.launch{
            repository.updateNote(note)
        }

    fun getAllNotes() = repository.getAllNotes()
    fun searchNote(query:String?) = repository.searchNote(query)

}
