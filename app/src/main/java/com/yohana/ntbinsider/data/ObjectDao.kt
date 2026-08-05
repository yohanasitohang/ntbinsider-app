package com.yohana.ntbinsider.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ObjectDao {
    @Query("SELECT * FROM tour")
    fun getAllTour(): Flow<List<ObjectEntity>>

    @Query("SELECT * FROM tour WHERE isFavorite = 1")
    fun getAllFavoriteTour(): Flow<List<ObjectEntity>>

    @Query("SELECT * FROM tour WHERE id = :id")
    fun getTour(id: Int): Flow<ObjectEntity>

    @Query("SELECT * FROM tour WHERE name LIKE '%' || :query || '%'")
    fun searchTour(query: String): Flow<List<ObjectEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTour(tourList: List<ObjectEntity>)

    @Query("UPDATE tour SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavoriteTour(id: Int, isFavorite: Boolean)
}