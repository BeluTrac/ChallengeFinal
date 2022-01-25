package com.belutrac.challengefinal.main

import com.belutrac.challengefinal.Team
import com.belutrac.challengefinal.api.TeamsJsonResponse
import com.belutrac.challengefinal.api.service
import com.belutrac.challengefinal.database.TeamDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository(private val database: TeamDatabase) {

    suspend fun fetchTeams(): MutableList<Team> {
        return withContext(Dispatchers.IO) {
            val teamsJsonResponse = service.getTeams()
            val parsedList = parseTeamResult(teamsJsonResponse)
            database.teamDao.insertAll(parsedList)
            fetchTeamsByDatabase()
        }
    }

    suspend fun fetchTeamsByDatabase() : MutableList<Team>{
        return withContext(Dispatchers.IO){
            database.teamDao.getTeams()
        }
    }

    private fun parseTeamResult(teamsJsonResponse: TeamsJsonResponse): MutableList<Team>{
        val teams = teamsJsonResponse.teams
        val teamList = mutableListOf<Team>()

        for (team in teams) {
            teamList.add(Team(team.idTeam, team.strTeam,team.strTeamBadge))
        }

        return teamList
    }
}