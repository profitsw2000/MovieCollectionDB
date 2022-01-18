package ru.profitsw2000.moviecollectiondb.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import ru.profitsw2000.moviecollectiondb.databinding.MainFragmentBinding
import ru.profitsw2000.moviecollectiondb.model.AppState
import ru.profitsw2000.moviecollectiondb.model.representation.Movie
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.profitsw2000.moviecollectiondb.R
import ru.profitsw2000.moviecollectiondb.ui.adapters.ParentAdapter
import ru.profitsw2000.moviecollectiondb.ui.description.DescriptionFragment

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModel()
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private var adapter: ParentAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            mainFragmentRecyclerView.adapter = adapter
        }
        val observer = Observer<AppState> { renderData(it) }
        viewModel.getLiveData().observe(viewLifecycleOwner, observer)
        viewModel.getMovie()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderData(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.Success -> {
                progressBar.visibility = View.GONE
                adapter = ParentAdapter(object : OnItemViewClickListener {
                    override fun onItemViewClick(movie: Movie) {
                        Toast.makeText(activity,"Clicked!!!", Toast.LENGTH_LONG).show()
/*                        val manager = activity?.supportFragmentManager
                        manager?.let { manager ->
                            val bundle = Bundle().apply {
                                //putParcelable(DetailsFragment.BUNDLE_EXTRA, weather)
                            }
                            manager.beginTransaction()
                                .add(R.id.container, DescriptionFragment.newInstance(bundle))
                                .addToBackStack("")
                                .commitAllowingStateLoss()
                        }*/
                    }
                }).apply {
                    setMovie(appState.moviesData)
                }
                mainFragmentRecyclerView.adapter = adapter
            }
            is AppState.Loading -> {
                progressBar.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                val message = appState.message
                progressBar.visibility = View.GONE
                Snackbar
                    .make(main, message, Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload") { viewModel.getMovie() }
                    .show()
            }
        }
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(movie: Movie)
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}