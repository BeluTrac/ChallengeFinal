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

class MapsFragment : Fragment() {

    private lateinit var viewModel : MapViewModel
    private val callback = OnMapReadyCallback { googleMap ->

        viewModel.teamsList.observe(requireActivity(), Observer {
        it ->
            it.forEach {
                if(!it.location.isNullOrBlank())
                {
                    val location = getLocationFromAddress(it.location)
                    googleMap.addMarker(MarkerOptions().position(location).title("Marker in Sydney"))
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(location))
                }
            }
        })
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
        mapFragment?.getMapAsync(callback)
    }

   fun getLocationFromAddress(address: String): LatLng{
       val coder = Geocoder(requireActivity())
       val address = coder.getFromLocationName(address,1)
       return LatLng(address[0].latitude, address[0].longitude)
   }
}