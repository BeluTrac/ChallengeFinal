package com.belutrac.challengefinal.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.belutrac.challengefinal.Team
import com.belutrac.challengefinal.api.ApiResponseStatus
import com.belutrac.challengefinal.database.Favorites
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class MainViewModel (application: Application): AndroidViewModel(application) {

    private var _teamsList = MutableLiveData<MutableList<Team>>()
    val teamsList : LiveData<MutableList<Team>>
        get() = _teamsList

    private val _statusLiveData = MutableLiveData<ApiResponseStatus>()
    val statusLiveData: LiveData<ApiResponseStatus>
        get() = _statusLiveData

    private var repository = MainRepository(application)

    fun reloadTeams() {
        viewModelScope.launch {
            _statusLiveData.value = ApiResponseStatus.LOADING
            try {
                val teams = repository.fetchTeams()
                val favs = repository.fetchFavorites()
                _teamsList.value = parseFavs(teams,favs)
                _statusLiveData.value = ApiResponseStatus.DONE
            } catch (e: UnknownHostException) {
                if (teamsList.value == null || teamsList.value!!.isEmpty()) {
                    _statusLiveData.value = ApiResponseStatus.NOT_INTERNET_CONNECTION
                } else {
                    _statusLiveData.value = ApiResponseStatus.DONE
                }
            }
        }
    }

    fun updateFavorite(team : Team){
        viewModelScope.launch {
            repository.updateFavoriteTeam(team.id, !team.isFav)

            val teamLisAux = _teamsList.value
            teamLisAux?.forEach {
                if(it.id == team.id)
                {
                    it.isFav = !it.isFav
                }
            }
        }
    }

    fun searchTeamsByName(newText: String) {
        viewModelScope.launch {
            val teams = repository.fetchTeamsByDescription(newText)
            val favs = repository.fetchFavorites()
            _teamsList.value = parseFavs(teams,favs)
        }
    }

    fun reloadTeamsFromDatabase() {
        viewModelScope.launch {
            val teams = repository.fetchTeamsByDatabase()
            if (teams.isEmpty())
            {
                reloadTeams()
            }else
            {
                val favs = repository.fetchFavorites()
                _teamsList.value = parseFavs(teams,favs)
            }
        }
    }

    private fun parseFavs(teams: MutableList<Team>, favs: MutableList<Favorites>): MutableList<Team> {
        for (myTeam in teams){
            myTeam.isFav = favs.map { it.id }.contains(myTeam.id)
        }
        return teams
    }
}