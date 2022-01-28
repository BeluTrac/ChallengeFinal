package com.belutrac.challengefinal.database

import androidx.room.*
import com.belutrac.challengefinal.Team

@Dao
interface TeamDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(teamList: MutableList<Team>)

    @Query("SELECT * FROM Teams ")
    fun getTeams(): MutableList<Team>

    @Query("SELECT * from Teams WHERE name LIKE '%'|| :query ||'%'")
    suspend fun getTeamsByDescription(query: String): MutableList<Team>
}