package com.example.dreams

import android.content.Context
import androidx.room.Room

class DreamSingl private constructor(){
    companion object{
        private var dao: DreamDao? = null
        fun instance(context: Context): DreamDao?{
            if(dao==null){
                val db = Room.databaseBuilder(context,
                    DreamDatabase::class.java, "dreams-database").build()
                dao = db.getPersonDao()
            }
            return dao
        }
        fun getDao(): DreamDao?{
            return dao
        }
    }
}