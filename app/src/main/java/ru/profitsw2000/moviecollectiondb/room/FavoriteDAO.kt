package ru.profitsw2000.moviecollectiondb.room

import androidx.room.*

@Dao
interface FavoriteDAO {

    @Query("SELECT * FROM FavoriteEntity")
    suspend fun all(): List<FavoriteEntity>

    @Query("SELECT * FROM FavoriteEntity WHERE title LIKE :title")
    suspend fun getDataByWord(title: String): List<FavoriteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: FavoriteEntity)

    @Update
    suspend fun update(entity: FavoriteEntity)

    @Delete
    suspend fun delete(entity: FavoriteEntity)
}