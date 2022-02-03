package ru.profitsw2000.moviecollectiondb.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val movieID: Int,
    val movieNote: String
)
