package ru.profitsw2000.moviecollectiondb.ui.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.profitsw2000.moviecollectiondb.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.profitsw2000.moviecollectiondb.databinding.FragmentNoteBinding
import ru.profitsw2000.moviecollectiondb.model.AppState
import ru.profitsw2000.moviecollectiondb.ui.adapters.NotesAdapter

class NoteFragment : Fragment() {

    private val viewModel: NoteViewModel by viewModel()
    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!
    private val adapter: NotesAdapter by lazy { NotesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.noteFragmentRecyclerview.adapter = adapter

        viewModel.movieNoteLiveData.observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getMovieNotes()
    }

    private fun renderData(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.NotesSuccess -> {
                progressBar.hide()
                noteFragmentRecyclerview.show()
                adapter.setData(appState.noteList)
            }
            is AppState.Loading -> {
                progressBar.show()
                noteFragmentRecyclerview.hide()
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
        fun newInstance() = NoteFragment()
    }
}