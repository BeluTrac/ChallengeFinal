package com.belutrac.challengefinal.main

import android.util.Log
import com.belutrac.challengefinal.Team
import com.belutrac.challengefinal.api.TeamsJsonResponse
import com.belutrac.challengefinal.api.service

class MainRepository {

    suspend fun fetchTeams(): MutableList<Team> {
        val teamsJsonResponse = service.getTeams()
        val parsedList = parseTeamResult(teamsJsonResponse)
        Log.d("REPOSITORY-DEBUG",parsedList.toString())
        return parsedList
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