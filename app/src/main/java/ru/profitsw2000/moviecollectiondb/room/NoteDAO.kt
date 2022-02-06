package ru.profitsw2000.moviecollectiondb.room

import androidx.room.*

@Dao
interface NoteDAO {

    @Query("SELECT * FROM NoteEntity")
    suspend fun all(): List<NoteEntity>

    @Query("SELECT * FROM NoteEntity WHERE title LIKE :title")
    suspend fun getDataByWord(title: String): List<NoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: NoteEntity)

    @Update
    suspend fun update(entity: NoteEntity)

    @Delete
    suspend fun delete(entity: NoteEntity)
}