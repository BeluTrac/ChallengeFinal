package com.belutrac.challengefinal.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.net.UnknownHostException

private val TAG = MainViewModel::class.java.simpleName

class MainViewModel (application: Application): AndroidViewModel(application) {

    fun reloadTeams()
    {
        val repository = MainRepository()
        viewModelScope.launch {
            try {
                var teams = repository.fetchTeams()

            } catch (e: UnknownHostException) {
                Log.d(TAG, "No internet connection")
            }
        }
    }
}