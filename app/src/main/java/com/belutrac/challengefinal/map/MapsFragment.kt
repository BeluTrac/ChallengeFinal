package com.belutrac.challengefinal.map

import android.location.Address
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.belutrac.challengefinal.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.location.Geocoder
import android.util.Log


class MapsFragment : Fragment() {

    private val callback = OnMapReadyCallback { googleMap ->
        var location = getLocationFromAddress()
        googleMap.addMarker(MarkerOptions().position(location).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

   fun getLocationFromAddress() : LatLng{
       var coder = Geocoder(requireActivity())
       var address = coder.getFromLocationName("Buenos Aires, Argentina",1)
       var long = address[0].longitude
       var lat = address[0].latitude
       return LatLng(lat, long)
   }
}