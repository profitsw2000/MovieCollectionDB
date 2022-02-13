package ru.profitsw2000.moviecollectiondb.ui.maps

import android.app.AlertDialog
import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.marginBottom
import androidx.core.view.setPadding
import androidx.lifecycle.Observer

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import ru.profitsw2000.moviecollectiondb.R
import ru.profitsw2000.moviecollectiondb.databinding.FragmentMapsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.profitsw2000.moviecollectiondb.model.AppState
import ru.profitsw2000.moviecollectiondb.model.representation.Actor
import ru.profitsw2000.moviecollectiondb.model.representation_tmdb.ActorDTO
import java.io.IOException

class MapsFragment : Fragment() {

    private val viewModel: MapsViewModel by viewModel()
    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!
    private lateinit var map: GoogleMap
    private lateinit var input: String
    private var finded = false

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap

        val spb = LatLng(59.9417277, 30.0937839)
        googleMap.addMarker(MarkerOptions().position(spb).title("Marker in Saint-Petersburg"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(spb))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapsBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        val observer = Observer<AppState> { renderData(it) }
        viewModel.mapLiveData.observe(viewLifecycleOwner, observer)

        with(binding){
            buttonSearch.setOnClickListener {
                val personName = searchActor.text.toString()
                if (!personName.isNullOrEmpty()) {
                    input = personName
                    finded = false
                    mapLinear.hide()
                    containerForNames.show()
                    containerForNames.removeAllViewsInLayout()
                    viewModel.getPeople(personName)
                }
            }
        }
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                //showDialog("Error",appState.message)
                with(binding){
                    progressBar.hide()
                }
            }
            AppState.Loading -> {
                with(binding){
                    mapLinear.hide()
                    containerForNames.hide()
                    progressBar.show()
                }
            }
            is AppState.ActorSuccess -> {
                with(binding){
                    containerForNames.show()
                    progressBar.hide()
                }
                val actor = appState.actorDetails
                var actorName = ""

                for (name in actor.also_known_as!!){
                    if (name.contains(input,true)){
                        actorName = name
                        if (!finded) {
                            binding.containerForNames.removeAllViewsInLayout()
                            finded = true
                        }
                    }
                }

                addView(requireContext(), Actor(actorName, actor.place_of_birth))
            }
            else -> {

            }
        }
    }

    private fun addView(context: Context, actor: Actor) {
        binding.containerForNames.addView(TextView(context).apply {
            if(finded) {
                text = actor.name
                textSize = 20f
                setPadding(8,8,8,8)
                setOnClickListener{
                    with(binding){
                        mapLinear.show()
                        containerForNames.hide()
                        containerForNames.removeAllViewsInLayout()
                    }
                    initSearchByAddress(actor.birthPlace!!)

                    //text = actor.birthPlace
                }
            }
            else{
                binding.containerForNames.removeAllViewsInLayout()
                text = "Person not found"
                setPadding(8,8,8,8)
                textSize = 20f
            }
        })
    }

    private fun View.show() : View {
        if (visibility != View.VISIBLE) {
            visibility = View.VISIBLE
        }
        return this
    }

    private fun View.hide() : View {
        if (visibility != View.GONE) {
            visibility = View.GONE
        }
        return this
    }

    private fun initSearchByAddress(birthPlace: String) {
        val geoCoder = Geocoder(context)
        Thread {
            try {
                val addresses = geoCoder.getFromLocationName(birthPlace, 1)
                if (addresses.size > 0) {
                    goToAddress(addresses, binding.root, birthPlace)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
    }

    private fun goToAddress(addresses: List<Address>, view: View, birthPlace: String) {
        val location = LatLng(addresses[0].latitude,addresses[0].longitude)
        view.post {
            setMarker(location, birthPlace)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom( location,8f))
        }
    }

    private fun setMarker(location: LatLng, place: String) {
        map.addMarker(
            MarkerOptions()
                .position(location)
                .title(place)
        )
    }

    private fun showDialog(title: String, message: String) {
        activity?.let {
            AlertDialog.Builder(it)
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton(getString(R.string.dialog_button_text_close)) {
                        dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        }
    }

    companion object {
        fun newInstance() = MapsFragment()
    }
}