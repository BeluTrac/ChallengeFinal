package com.belutrac.challengefinal.map

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.belutrac.challengefinal.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.location.Geocoder
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.GoogleMap

class MapsFragment : Fragment() {

    private lateinit var viewModel : MapViewModel
    private lateinit var latlngList : MutableList<LatLng>
    private val callback = OnMapReadyCallback { googleMap ->

        val itemMap = viewModel.itemMapLiveData.value

        itemMap?.run {
            val marker = MarkerOptions().position(itemMap.latlng).title(itemMap.team.name)
            if(itemMap.team.stadiumName.isNotBlank())
            {
                marker.snippet(itemMap.team.stadiumName)
            }
            googleMap.addMarker(marker)
        }

        if(viewModel.teamsList.value?.size!! > 1) {
                viewModel.updateMap()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[MapViewModel::class.java]

        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?


        viewModel.itemMapLiveData.observe(requireActivity()){
            mapFragment?.getMapAsync(callback)
        }

        viewModel.teamsList.observe(requireActivity()){
            viewModel.updateMap()
        }

        }
    }

