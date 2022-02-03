package ru.profitsw2000.moviecollectiondb.room

import androidx.room.*

@Dao
interface NoteDAO {

    @Query("SELECT * FROM NoteEntity")
    fun all(): List<NoteEntity>

    @Query("SELECT * FROM NoteEntity WHERE title LIKE :title")
    fun getDataByWord(title: String): List<NoteEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(entity: NoteEntity)

    @Update
    fun update(entity: NoteEntity)

    @Delete
    fun delete(entity: NoteEntity)
}