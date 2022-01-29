package ru.profitsw2000.moviecollectiondb.ui.description

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModel
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.snackbar.Snackbar
import ru.profitsw2000.moviecollectiondb.R
import ru.profitsw2000.moviecollectiondb.databinding.FragmentDescriptionBinding
import ru.profitsw2000.moviecollectiondb.model.AppState
import ru.profitsw2000.moviecollectiondb.model.representation.Movie
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.logger.MESSAGE
import ru.profitsw2000.moviecollectiondb.services.DescriptionService
import ru.profitsw2000.moviecollectiondb.services.MOVIE_ID_EXTRA

const val DESCRIPTION_INTENT_FILTER = "DESCRIPTION INTENT FILTER"
const val DESCRIPTION_LOAD_RESULT_EXTRA = "DESCRIPTION LOAD RESULT EXTRA"
const val DESCRIPTION_RESPONSE_SUCCESS_EXTRA = "DESCRIPTION RESPONSE SUCCESS EXTRA"
const val DESCRIPTION_RESPONSE_EMPTY_EXTRA = "DESCRIPTION RESPONSE EMPTY EXTRA"
const val DESCRIPTION_INTENT_EMPTY_EXTRA = "DESCRIPTION INTENT EMPTY EXTRA"
const val DESCRIPTION_DATA_EMPTY_EXTRA = "DESCRIPTION DATA EMPTY EXTRA"

const val DESCRIPTION_BUDGET_EXTRA = "DESCRIPTION BUDGET EXTRA"
const val DESCRIPTION_GENRES_EXTRA = "DESCRIPTION GENRES EXTRA"
const val DESCRIPTION_ID_EXTRA = "DESCRIPTION ID EXTRA"
const val DESCRIPTION_OVERVIEW_EXTRA = "DESCRIPTION OVERVIEW EXTRA"
const val DESCRIPTION_RELEASE_DATE_EXTRA = "DESCRIPTION RELEASE DATE EXTRA"
const val DESCRIPTION_REVENUE_EXTRA = "DESCRIPTION REVENUE EXTRA"
const val DESCRIPTION_RUNTIME_EXTRA = "DESCRIPTION RUNTIME EXTRA"
const val DESCRIPTION_TITLE_EXTRA = "DESCRIPTION TITLE EXTRA"
const val DESCRIPTION_VOTE_AVERAGE_EXTRA = "DESCRIPTION VOTE AVERAGE EXTRA"

private const val PROCESS_ERROR = "Обработка ошибки"
private const val BUDGET_ERROR = -1
private const val ID_ERROR = -1
private const val REVENUE_ERROR = -1
private const val RUNTIME_ERROR = -1
private const val VOTE_AVERAGE_ERROR = -1.0f

private const val ACTION_SEND_MSG = "ru.profitsw2000.message.sender"
private const val MESSAGE_NAME = "MOVIE TITLE"
private const val FLAG_RECEIVER_INCLUDE_BACKGROUND = 0x01000000

class DescriptionFragment : Fragment() {

    //import library before introduce viewModel!!!
    private val viewModel: DescriptionViewModel by viewModel()
    private var _binding: FragmentDescriptionBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieBundle: Movie
    private val loadResultsReceiver: BroadcastReceiver = object :
        BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.getStringExtra(DESCRIPTION_LOAD_RESULT_EXTRA)) {
                DESCRIPTION_RESPONSE_SUCCESS_EXTRA -> renderData(
                    Movie(
                        intent.getStringExtra(DESCRIPTION_TITLE_EXTRA)!!,
                        intent.getStringExtra(DESCRIPTION_GENRES_EXTRA)!!,
                        intent.getIntExtra(DESCRIPTION_RUNTIME_EXTRA, RUNTIME_ERROR),
                        intent.getFloatExtra(DESCRIPTION_VOTE_AVERAGE_EXTRA, VOTE_AVERAGE_ERROR),
                        intent.getIntExtra(DESCRIPTION_BUDGET_EXTRA, BUDGET_ERROR),
                        intent.getIntExtra(DESCRIPTION_REVENUE_EXTRA, REVENUE_ERROR),
                        intent.getStringExtra(DESCRIPTION_RELEASE_DATE_EXTRA)!!,
                        intent.getStringExtra(DESCRIPTION_OVERVIEW_EXTRA)!!,
                        R.drawable.film,
                        intent.getIntExtra(DESCRIPTION_ID_EXTRA, ID_ERROR),
                        )
                    )
                else -> TODO(PROCESS_ERROR)
            }
        }
    }

    private fun renderData(movie: Movie) {
        if(movie.budget == BUDGET_ERROR ||
            movie.duration == RUNTIME_ERROR ||
            movie.rating == VOTE_AVERAGE_ERROR ||
            movie.revenue == REVENUE_ERROR ||
            movie.id == ID_ERROR ||
            movie.title == null ||
            movie.genre == null ||
            movie.releaseDate == null ||
            movie.description == null
        ) {
            TODO(PROCESS_ERROR)
        } else {
            with(binding){
                progressBar.hide()
                setData(movie)
                movieDescriptionGroup.show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            LocalBroadcastManager.getInstance(it)
                .registerReceiver(loadResultsReceiver,
                    IntentFilter(DESCRIPTION_INTENT_FILTER)
                )
        }
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
        with(binding){
            movieDescriptionGroup.hide()
            progressBar.show()
            sendBroadcastMessage.setOnClickListener{
                if (movieBundle != null){
                    val movieTitle = movieBundle.title
                    val intent = Intent()
                    intent.setAction(ACTION_SEND_MSG)
                    intent.putExtra(MESSAGE_NAME,movieTitle)
                    intent.addFlags(FLAG_RECEIVER_INCLUDE_BACKGROUND)
                    activity?.sendBroadcast(intent)
                }
            }
        }

        movieBundle = arguments?.getParcelable<Movie>(BUNDLE_EXTRA) ?: Movie()

        context?.let {
            it.startService(Intent(it, DescriptionService::class.java).apply {
                putExtra(MOVIE_ID_EXTRA,movieBundle.id)
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        context?.let {
            LocalBroadcastManager.getInstance(it).unregisterReceiver(loadResultsReceiver)
        }
        super.onDestroy()
    }

    private fun setData(movie: Movie) = with(binding) {
        title.text = movie.title
        appCompatImageView.setImageResource(movie.picture)
        genre.text = movie.genre
        duration.text = movie.duration.toString() + " мин."
        rating.text = movie.rating.toString()
        budget.text = String.format("%,d",movie.budget) + " $"
        revenue.text = String.format("%,d",movie.revenue) + " $"
        releaseDate.text = movie.releaseDate
        description.text = movie.description

        movieDescriptionGroup.show()
        progressBar.hide()
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