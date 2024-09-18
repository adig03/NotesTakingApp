package com.example.notestakingapp.Repository

import androidx.lifecycle.LiveData
import com.example.notestakingapp.room.Note
import com.example.notestakingapp.room.NoteDB

class NoteRepository(private val notesDB: NoteDB) {

    suspend fun insertNote(note: Note): Long {
        return notesDB.getNotesDao().insert(note)
    }

    suspend fun updateNote(note: Note) {
        return notesDB.getNotesDao().update(note)
    }

    suspend fun deleteNote(note: Note) {
        return notesDB.getNotesDao().delete(note)
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return notesDB.getNotesDao().getAllNotesInDB()
    }

    fun searchNote(query: String?): LiveData<List<Note>> {
        return notesDB.getNotesDao().searchNotes(query)
    }
}


