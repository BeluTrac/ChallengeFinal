package com.belutrac.challengefinal.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.belutrac.challengefinal.Team

@Database(entities = [Team::class, Favorites::class],version = 1)
abstract class  TeamDatabase : RoomDatabase(){
    abstract val teamDao : TeamDao
    abstract val favDao : FavDao
}

private lateinit var INSTANCE: TeamDatabase

fun getDatabase(context: Context): TeamDatabase{
    synchronized(TeamDatabase::class.java){
        if(!::INSTANCE.isInitialized){
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                TeamDatabase::class.java,
                "team.db"
            ).build()
        }
        return INSTANCE
    }
}

