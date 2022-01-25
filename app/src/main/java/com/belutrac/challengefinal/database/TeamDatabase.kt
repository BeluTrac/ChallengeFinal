package com.belutrac.challengefinal.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.belutrac.challengefinal.Team
import androidx.sqlite.db.SupportSQLiteDatabase

import androidx.room.migration.Migration




@Database(entities = [Team::class],version = 2)
abstract class  TeamDatabase : RoomDatabase(){
    abstract val teamDao : TeamDao
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

