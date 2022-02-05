package ru.profitsw2000.moviecollectiondb.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(NoteEntity::class), version = 1, exportSchema = true)
abstract class NoteDB : RoomDatabase() {
    abstract fun noteDao(): NoteDAO
}