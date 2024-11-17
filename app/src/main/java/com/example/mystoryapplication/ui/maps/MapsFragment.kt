package com.example.mystoryapplication.ui.maps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mystoryapplication.MapsViewModelFactory
import com.example.mystoryapplication.R
import com.example.mystoryapplication.api.Injection
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {

    private val viewModel: MapsViewModel by viewModels {
        MapsViewModelFactory(Injection.provideRepository(requireContext()))
    }

    private val callback = OnMapReadyCallback { googleMap ->
        viewModel.storiesWithLocation.observe(viewLifecycleOwner) { stories ->
            stories.forEach { story ->
                val latLng = LatLng(story.lat!!, story.lon!!)
                googleMap.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(story.name)
                        .snippet(story.description)
                )
            }

            val builder = LatLngBounds.Builder()
            stories.forEach { story ->
                builder.include(LatLng(story.lat!!, story.lon!!))
            }
            val bounds = builder.build()
            googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}