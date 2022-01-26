package ru.profitsw2000.moviecollectiondb.ui.description

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.profitsw2000.moviecollectiondb.R
import ru.profitsw2000.moviecollectiondb.databinding.FragmentDescriptionBinding
import ru.profitsw2000.moviecollectiondb.model.representation.Movie

/**
 * A simple [Fragment] subclass.
 * Use the [DescriptionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DescriptionFragment : Fragment() {

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
            setData(it)
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

    companion object {
        const val BUNDLE_EXTRA = "movie"

        fun newInstance(bundle: Bundle): DescriptionFragment {
            val fragment = DescriptionFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}