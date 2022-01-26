package ru.profitsw2000.moviecollectiondb.ui.description

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import ru.profitsw2000.moviecollectiondb.R
import ru.profitsw2000.moviecollectiondb.databinding.FragmentDescriptionBinding
import ru.profitsw2000.moviecollectiondb.model.AppState
import ru.profitsw2000.moviecollectiondb.model.representation.Movie
import org.koin.androidx.viewmodel.ext.android.viewModel

class DescriptionFragment : Fragment() {

    //import library before introduce viewModel!!!
    private val viewModel: DescriptionViewModel by viewModel()
    //private lateinit var binding: FragmentDescriptionBinding
    private var _binding: FragmentDescriptionBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Movie>(BUNDLE_EXTRA)?.let {
            val id = it.id

            viewModel.movieLiveData.observe(viewLifecycleOwner, { appState ->
                with(binding) {
                when (appState) {
                        is AppState.Error -> {
                            val message = appState.message
                            movieDescriptionGroup.hide()
                            progressBar.hide()
                            info.showSnackBar(message, getString(R.string.snack_bar_reload), { viewModel.getMovieDescription(id) }, Snackbar.LENGTH_INDEFINITE)
                        }
                        AppState.Loading -> {
                            movieDescriptionGroup.hide()
                            progressBar.show()
                        }
                        is AppState.Success -> {

                        }
                        is AppState.MovieSuccess -> {
                            progressBar.hide()
                            setData(appState.movie)
                            movieDescriptionGroup.show()
                        }
                    }

                }
            })
            viewModel.getMovieDescription(id)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setData(movie: Movie) = with(binding) {
        title.text = movie.title
        appCompatImageView.setImageResource(movie.picture)
        genre.text = movie.genre
        duration.text = movie.duration.toString() + " мин."
        rating.text = movie.rating.toString()
        budget.text = movie.budget.toString() + " $"
        revenue.text = movie.revenue.toString() + " $"
        releaseDate.text = movie.releaseDate
        description.text = movie.description
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
    companion object {
        const val BUNDLE_EXTRA = "movie"

        fun newInstance(bundle: Bundle): DescriptionFragment {
            val fragment = DescriptionFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}