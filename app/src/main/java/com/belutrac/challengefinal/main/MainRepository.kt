package com.belutrac.challengefinal.main

import android.app.Application
import com.belutrac.challengefinal.Team
import com.belutrac.challengefinal.api.TeamsJsonResponse
import com.belutrac.challengefinal.api.service
import com.belutrac.challengefinal.database.Favorites
import com.belutrac.challengefinal.database.TeamDatabase
import com.belutrac.challengefinal.database.getDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository(application: Application) {
    private var database : TeamDatabase = getDatabase(application)

    suspend fun fetchTeams(): MutableList<Team> {
        return withContext(Dispatchers.IO) {
            val teamsJsonResponse = service.getTeams()
            val parsedList = parseTeamResult(teamsJsonResponse)
            database.teamDao.insertAll(parsedList)
            fetchTeamsByDatabase()
        }
    }

    suspend fun fetchTeamsByDatabase() : MutableList<Team> {
        return withContext(Dispatchers.IO){
            database.teamDao.getTeams()
        }
    }

    suspend fun fetchFavTeams() : List<Team> {
        return withContext(Dispatchers.IO){
            val teams = database.teamDao.getTeams()
            val favs = database.favDao.getFavs()

            teams.filter { it -> favs.map { it.id }.contains(it.id) }
        }
    }

    suspend fun fetchFavorites() : MutableList<Favorites>
    {
        return withContext(Dispatchers.IO){
            database.favDao.getFavs()
        }
    }

    suspend fun fetchTeamsByDescription(query: String) : MutableList<Team> {
        return withContext(Dispatchers.IO){
            database.teamDao.getTeamsByDescription(query)
        }
    }

    suspend fun updateFavoriteTeam(id : String,isFav :Boolean){
        withContext(Dispatchers.IO)
        {
           if(isFav){
               database.favDao.insert(Favorites(id))
           }
            else
           {
               database.favDao.delete(id)
           }
        }
    }

    private fun parseTeamResult(teamsJsonResponse: TeamsJsonResponse): MutableList<Team>{
        val teams = teamsJsonResponse.teams
        val teamList = mutableListOf<Team>()

        for (team in teams) {
            val id = team.idTeam
            val name = team.strTeam ?: ""
            val formedYear = team.intFormedYear ?: ""
            val imgUrl = team.strTeamBadge ?: ""
            val stadiumName = team.strStadium ?: ""
            val stadiumCap = team.intStadiumCapacity ?: ""
            val stadiumLocation = team.strStadiumLocation ?: ""
            val description = team.strDescriptionEN ?: ""
            val website = team.strWebsite ?: ""
            val myTeam = Team(id, name, formedYear,imgUrl,stadiumName,stadiumCap,stadiumLocation,description,website, false)
            teamList.add(myTeam)
        }
        return teamList
    }
}