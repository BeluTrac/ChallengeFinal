package com.belutrac.challengefinal.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.belutrac.challengefinal.Team
import com.belutrac.challengefinal.main.MainRepository
import kotlinx.coroutines.launch

class FavViewModel(application: Application) : AndroidViewModel(application) {

    private var _favList = MutableLiveData<MutableList<Team>>()
    val favList: LiveData<MutableList<Team>>
        get() = _favList
    private val repository = MainRepository(application)

    init {
        loadFavorites()
    }

    fun loadFavorites() {
        viewModelScope.launch {
            _favList.value = repository.fetchFavTeams().toMutableList()
        }
    }

}