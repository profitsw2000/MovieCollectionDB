package ru.profitsw2000.moviecollectiondb.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String
)
