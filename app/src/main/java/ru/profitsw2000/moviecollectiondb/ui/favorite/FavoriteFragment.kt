package ru.profitsw2000.moviecollectiondb.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.profitsw2000.moviecollectiondb.R
import ru.profitsw2000.moviecollectiondb.databinding.FragmentFavoriteBinding
import ru.profitsw2000.moviecollectiondb.databinding.FragmentNoteBinding
import ru.profitsw2000.moviecollectiondb.model.AppState
import ru.profitsw2000.moviecollectiondb.ui.adapters.FavoriteAdapter
import ru.profitsw2000.moviecollectiondb.ui.adapters.NotesAdapter
import ru.profitsw2000.moviecollectiondb.ui.notes.NoteFragment
import ru.profitsw2000.moviecollectiondb.ui.notes.NoteViewModel

class FavoriteFragment : Fragment() {

    private val viewModel: FavoriteViewModel by viewModel()
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val adapter: FavoriteAdapter by lazy { FavoriteAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.favoriteFragmentRecyclerview.adapter = adapter

        viewModel.favoriteMovieLiveData.observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getFavoriteMovies()
    }

    private fun renderData(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.FavoriteSuccess -> {
                progressBar.hide()
                favoriteFragmentRecyclerview.show()
                adapter.setData(appState.favoriteList)
            }
            is AppState.Loading -> {
                progressBar.show()
                favoriteFragmentRecyclerview.hide()
            }
            is AppState.Error -> {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        @JvmStatic
        fun newInstance() = FavoriteFragment()
    }
}