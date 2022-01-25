package com.belutrac.challengefinal.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.belutrac.challengefinal.Team
import kotlinx.coroutines.launch
import java.net.UnknownHostException

private val TAG = MainViewModel::class.java.simpleName

class MainViewModel (application: Application): AndroidViewModel(application) {

    private var _teamsList = MutableLiveData<MutableList<Team>>()
    val teamsList : LiveData<MutableList<Team>>
        get() = _teamsList

    fun reloadTeams() {
        val repository = MainRepository()
        viewModelScope.launch {
            try {
                val teams = repository.fetchTeams()
                _teamsList.value = teams
            } catch (e: UnknownHostException) {
                Log.d(TAG, "No internet connection")
            }
        }
    }
}