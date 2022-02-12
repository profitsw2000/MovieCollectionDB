package ru.profitsw2000.moviecollectiondb.ui.maps

import android.content.Context
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
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

class MapsFragment : Fragment() {

    private val viewModel: MapsViewModel by viewModel()
    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!

    private val callback = OnMapReadyCallback { googleMap ->
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

            }
            AppState.Loading -> {

            }
            is AppState.ActorSuccess -> {
                val idList = appState.placeOfBirth
                for (i in idList){
                    addView(requireContext(), i.toString())
                }
            }
            else -> {

            }
        }
    }

    private fun addView(context: Context, textToShow: String) {
        binding.containerForNames.addView(AppCompatTextView(context).apply {
            text = textToShow
            textSize = 14f
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

    companion object {
        fun newInstance() = MapsFragment()
    }
}