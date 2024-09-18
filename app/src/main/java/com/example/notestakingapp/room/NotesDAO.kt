package com.example.notestakingapp.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NotesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note:Note):Long
    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM Note ORDER BY note_id")
     fun getAllNotesInDB() : LiveData<List<Note>>

    @Query("SELECT * FROM Note WHERE note_title LIKE :query OR note_desc LIKE :query")
    fun searchNotes(query: String?): LiveData<List<Note>>

}