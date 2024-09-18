package com.example.notestakingapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 2)
abstract class NoteDB : RoomDatabase() {

    abstract fun getNotesDao(): NotesDAO

    companion object {
        @Volatile
        private var INSTANCE: NoteDB? = null

        fun getInstance(context: Context): NoteDB {
            // Synchronized block ensures only one thread can execute this at a time.
            synchronized(this) {
                var instance = INSTANCE
                // If the instance is null, create a new database instance.
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NoteDB::class.java,
                        "Note_DB"
                    )


                        .build()

                    INSTANCE = instance
                }
                // Return the database instance.
                return instance
            }
        }
    }
}
