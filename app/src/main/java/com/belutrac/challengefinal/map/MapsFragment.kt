package com.belutrac.challengefinal.map

import androidx.fragment.app.Fragment
import android.os.Bundle
import com.belutrac.challengefinal.R
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import android.view.*
import androidx.lifecycle.ViewModelProvider

class MapsFragment : Fragment() {

    private lateinit var viewModel: MapViewModel
    private val callback = OnMapReadyCallback { googleMap ->

        val itemMap = viewModel.itemMapLiveData.value

        itemMap?.run {
            val marker = MarkerOptions().position(itemMap.latlng).title(itemMap.team.name)
            if (itemMap.team.stadiumName.isNotBlank()) {
                marker.snippet(itemMap.team.stadiumName)
            }
            googleMap.addMarker(marker)
        }

        if (viewModel.teamsList.value?.size!! > 1) {
            viewModel.updateMap()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[MapViewModel::class.java]
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?


        viewModel.itemMapLiveData.observe(requireActivity()) {
            mapFragment?.getMapAsync(callback)
        }

        viewModel.teamsList.observe(requireActivity()) {
            viewModel.updateMap()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.removeItem(R.id.search_view)
    }

}

