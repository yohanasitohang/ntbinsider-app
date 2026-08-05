package com.yohana.ntbinsider.repository

import com.yohana.ntbinsider.data.ObjectDao
import com.yohana.ntbinsider.data.ObjectEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ObjectRepository @Inject constructor(private val tourDao: ObjectDao) {
    fun getAllTour() = tourDao.getAllTour()
    fun getAllFavoriteTour() = tourDao.getAllFavoriteTour()
    fun getTour(id: Int) = tourDao.getTour(id)
    fun searchTour(query: String) = tourDao.searchTour(query)
    suspend fun insertAllTour(tour: List<ObjectEntity>) = tourDao.insertAllTour(tour)
    suspend fun updateFavoriteTour(id: Int, isFavorite: Boolean) = tourDao.updateFavoriteTour(id, isFavorite)
}