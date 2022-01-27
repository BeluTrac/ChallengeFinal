package com.belutrac.challengefinal.map

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.belutrac.challengefinal.Team
import com.belutrac.challengefinal.api.ApiResponseStatus
import com.belutrac.challengefinal.main.MainRepository
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class MapViewModel (application: Application): AndroidViewModel(application) {

    private var _teamsList = MutableLiveData<MutableList<Team>>()
    val teamsList : LiveData<MutableList<Team>>
        get() = _teamsList

    private var repository = MainRepository(application)

    init{
        loadTeams()
    }

    fun loadTeams() {
        viewModelScope.launch {
                _teamsList.value = repository.fetchTeamsByDatabase()
        }
    }
}