package ru.profitsw2000.moviecollectiondb.ui.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.snackbar.Snackbar
import ru.profitsw2000.moviecollectiondb.databinding.MainFragmentBinding
import ru.profitsw2000.moviecollectiondb.model.AppState
import ru.profitsw2000.moviecollectiondb.model.representation.Movie
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.profitsw2000.moviecollectiondb.R
import ru.profitsw2000.moviecollectiondb.receivers.ConnectivityReceiver
import ru.profitsw2000.moviecollectiondb.ui.adapters.ParentAdapter
import ru.profitsw2000.moviecollectiondb.ui.description.DescriptionFragment
import ru.profitsw2000.moviecollectiondb.ui.menu.SHOW_ADULT_KEY

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModel()
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private var adapter: ParentAdapter? = null
    private val receiver = ConnectivityReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.registerReceiver(receiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var includeAdult = false

        activity?.let {
            val sharedPref = it.getPreferences(Context.MODE_PRIVATE)
            includeAdult = sharedPref.getBoolean(SHOW_ADULT_KEY, false)
        }

        with(binding) {
            mainFragmentRecyclerView.adapter = adapter
        }
        val observer = Observer<AppState> { renderData(it, includeAdult) }
        viewModel.getLiveData().observe(viewLifecycleOwner, observer)
        viewModel.getMovieInfo(includeAdult)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        context?.unregisterReceiver(receiver)
        super.onDestroy()
    }

    private fun renderData(appState: AppState, includeAdult: Boolean) = with(binding) {
        when (appState) {
            is AppState.Success -> {
                progressBar.hide()
                adapter = ParentAdapter(object : OnItemViewClickListener {
                    override fun onItemViewClick(movie: Movie) {
                        val manager = activity?.supportFragmentManager
                        manager?.let { manager ->
                            val bundle = Bundle().apply {
                                putParcelable(DescriptionFragment.BUNDLE_EXTRA, movie)
                            }
                            manager.beginTransaction()
                                .replace(R.id.container, DescriptionFragment.newInstance(bundle))
                                .addToBackStack("")
                                .commitAllowingStateLoss()
                        }
                    }
                }).apply {
                    setCategories(appState.categoriesData)
                }
                mainFragmentRecyclerView.adapter = adapter
                mainFragmentRecyclerView.show()
            }
            is AppState.Loading -> {
                mainFragmentRecyclerView.hide()
                progressBar.show()
            }
            is AppState.Error -> {
                val message = appState.message
                progressBar.hide()
                mainFragmentRecyclerView.hide()
                main.showSnackBar(message, getString(R.string.snack_bar_reload), { viewModel.getMovieInfo(includeAdult) }, Snackbar.LENGTH_INDEFINITE)
            }
            else -> {

            }
        }
    }

    private fun View.showSnackBar (
        text: String,
        actionText: String,
        action: (View) -> Unit,
        length: Int = Snackbar.LENGTH_INDEFINITE
    ) {
        Snackbar.make(this, text, length).setAction(actionText, action).show()
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

    interface OnItemViewClickListener {
        fun onItemViewClick(movie: Movie)
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
