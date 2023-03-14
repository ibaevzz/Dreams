package com.example.dreams

import androidx.room.*

@Dao()
interface DreamDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dream: Dream)

    @Delete
    fun delete(dream: Dream)

    @Query("SELECT * FROM dream")
    fun getAllDreams(): List<Dream>?
}