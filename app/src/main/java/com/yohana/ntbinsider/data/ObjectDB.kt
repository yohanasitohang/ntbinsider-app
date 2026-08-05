package com.yohana.ntbinsider.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ObjectEntity::class], version = 1, exportSchema = false)
abstract class ObjectDB : RoomDatabase() {
    abstract fun objectDao(): ObjectDao
}