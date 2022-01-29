package com.belutrac.challengefinal.map

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.fragment.app.Fragment
import android.os.Bundle
import com.belutrac.challengefinal.R
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import android.view.*
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.floatingactionbutton.FloatingActionButton

private const val LOCATION_PERMISSION_REQUEST_CODE = 2000
private const val DEFAULT_MAP_SCALE = 4.0f
private const val DEFAULT_MAP_SCALE_USER = 13.0f
class MapsFragment : Fragment() {

    private lateinit var viewModel: MapViewModel
    private val userLocation = Location("")
    private val callback = OnMapReadyCallback { googleMap ->

        val itemMap = viewModel.itemMapLiveData.value

        itemMap?.run {
            val marker = MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).position(itemMap.latlng).title(itemMap.team.name)
            if (itemMap.team.stadiumName.isNotBlank()) {
                marker.snippet(itemMap.team.stadiumName)
            }
            googleMap.addMarker(marker)

        }

        if (viewModel.teamsList.value?.size!! > 1) {
            viewModel.updateMap()
        }

    }

    private val addUsercallback = OnMapReadyCallback { googleMap ->
        val userLatLng = LatLng(userLocation.latitude, userLocation.longitude)
        val userMarker = MarkerOptions().position(userLatLng)
        googleMap.addMarker(userMarker)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, DEFAULT_MAP_SCALE))

        requireActivity().findViewById<FloatingActionButton>(R.id.my_location_button).setOnClickListener {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, DEFAULT_MAP_SCALE_USER))
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[MapViewModel::class.java]
        setHasOptionsMenu(true)
        checkLocationPermission()

        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    private fun checkLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(requireActivity(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                getUserLocation()
            } else {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            }
        } else {
            getUserLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getUserLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                userLocation.latitude = location.latitude
                userLocation.longitude = location.longitude
                val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
                mapFragment?.getMapAsync(addUsercallback)
                setupMap()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            when {
                grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                    getUserLocation()
                }
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    showLocationPermissionRationaleDialog()
                }
                else -> {
                    findNavController().popBackStack()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showLocationPermissionRationaleDialog() {
        val dialog = AlertDialog.Builder(requireActivity())
            .setTitle(R.string.need_location_permission_dialog_title)
            .setMessage(R.string.need_location_permission_dialog_message)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE)
            }.setNegativeButton(R.string.no) { _, _ ->
                findNavController().popBackStack()
            }
        dialog.show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.removeItem(R.id.search_view)
    }

    private fun setupMap()
    {
        viewModel.teamsList.observe(requireActivity()) {
            viewModel.updateMap()
        }

        viewModel.itemMapLiveData.observe(requireActivity()) {
            val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
            mapFragment?.getMapAsync(callback)
        }

    }

}

