package com.belutrac.challengefinal.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.belutrac.challengefinal.Team

@Dao
interface TeamDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(teamList : MutableList<Team>)

    @Query("SELECT * FROM Teams")
    fun getTeams() : MutableList<Team>

}