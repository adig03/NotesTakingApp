package com.example.notestakingapp.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.sql.Timestamp

@Entity
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true)
    val note_id: Int,
    val note_title: String,
    val note_desc: String,
    @ColumnInfo("note_timestamp")
    val timestamp: String

) : Parcelable

