package ru.profitsw2000.moviecollectiondb.room

import androidx.room.Database

@Database(entities = arrayOf(NoteEntity::class), version = 1, exportSchema = false)
abstract class NoteDB {
    abstract fun noteDao(): NoteDAO
}