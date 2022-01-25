package com.belutrac.challengefinal.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.belutrac.challengefinal.Team
import com.belutrac.challengefinal.api.ApiResponseStatus
import com.belutrac.challengefinal.database.getDatabase
import kotlinx.coroutines.launch
import java.net.UnknownHostException

private val TAG = MainViewModel::class.java.simpleName

class MainViewModel (application: Application): AndroidViewModel(application) {

    private var _teamsList = MutableLiveData<MutableList<Team>>()
    val teamsList : LiveData<MutableList<Team>>
        get() = _teamsList

    private val _statusLiveData = MutableLiveData<ApiResponseStatus>()
    val statusLiveData: LiveData<ApiResponseStatus>
        get() = _statusLiveData

    private val database = getDatabase(application)
    private val repository = MainRepository(database)

    init{
        reloadTeams()
    }

    fun reloadTeams() {
        val repository = MainRepository(database)
        viewModelScope.launch {
            //TODO: NO ANDA SIN INTERNET
            _statusLiveData.value = ApiResponseStatus.LOADING
            try {
                _teamsList.value = repository.fetchTeams()
                _statusLiveData.value = ApiResponseStatus.DONE
            } catch (e: UnknownHostException) {
                if (teamsList.value == null || teamsList.value!!.isEmpty()) {
                    _statusLiveData.value = ApiResponseStatus.NO_INTERNET_CONNECTION
                } else {
                    _statusLiveData.value = ApiResponseStatus.DONE
                }
            }
        }
    }
}