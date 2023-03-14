package com.example.dreams

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ Dream::class ], version = 1)
abstract class DreamDatabase: RoomDatabase() {
    abstract fun getPersonDao(): DreamDao
}