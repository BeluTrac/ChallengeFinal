package com.belutrac.challengefinal.map

import android.app.Application
import android.location.Geocoder
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.belutrac.challengefinal.Team
import com.belutrac.challengefinal.main.ItemMap
import com.belutrac.challengefinal.main.MainRepository
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MapViewModel (application: Application): AndroidViewModel(application) {

    private var _teamsList = MutableLiveData<MutableList<Team>>()
    val teamsList : LiveData<MutableList<Team>>
        get() = _teamsList

    private var _itemMapLiveData = MutableLiveData<ItemMap>()
    val itemMapLiveData : LiveData<ItemMap>
        get() = _itemMapLiveData

    private var repository = MainRepository(application)

    init{
        loadTeams()
    }

    fun loadTeams() {
        viewModelScope.launch {
            _teamsList.value = repository.fetchTeamsByDatabase()
        }
    }

    fun getLocationFromAddress(address: String): LatLng {
        val coder = Geocoder(this.getApplication())
        val address = coder.getFromLocationName(address,1)
        return LatLng(address[0].latitude, address[0].longitude)}

    fun updateMap()
    {
        viewModelScope.launch {
            val itemMap : ItemMap
            withContext(Dispatchers.IO){
                val team = _teamsList.value?.removeFirst()

                if(team != null && team.location.isNotBlank())
                {
                    itemMap = ItemMap(team,getLocationFromAddress(team.location))
                }else
                {
                    itemMap = _itemMapLiveData.value!!
                }
            }
            _itemMapLiveData.value = itemMap
        }
    }
}