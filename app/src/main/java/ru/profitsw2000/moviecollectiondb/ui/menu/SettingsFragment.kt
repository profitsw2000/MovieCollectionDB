package ru.profitsw2000.moviecollectiondb.ui.menu

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.Toast
import ru.profitsw2000.moviecollectiondb.R
import org.koin.android.ext.android.inject
import ru.profitsw2000.moviecollectiondb.databinding.FragmentDescriptionBinding
import ru.profitsw2000.moviecollectiondb.databinding.FragmentSettingsBinding

const val SHOW_ADULT_KEY = "Show_adult"
const val SHOW_GENRES = "Show_genres"
const val GENRE_ACTION_MOVIE = 0
const val GENRE_ADVENTURES = 1
const val GENRE_CARTOONS = 2
const val GENRE_COMEDY = 3
const val GENRE_CRIMINAL = 4
const val GENRE_DOCUMENTARY = 5
const val GENRE_DRAMA = 6
const val GENRE_FAMILY = 7
const val GENRE_FANTASY = 8
const val GENRE_HISTORY = 9
const val GENRE_HORROR = 10
const val GENRE_MUSIC = 11
const val GENRE_DETECTIVE = 12
const val GENRE_MELODRAMA = 13
const val GENRE_FANTASTIC = 14
const val GENRE_TELEFILM = 15
const val GENRE_THRILLER = 16
const val GENRE_MILITARY = 17
const val GENRE_WESTERN = 18

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            val sharedPref = it.getPreferences(Context.MODE_PRIVATE)

            with(binding) {
                if (sharedPref.getBoolean(SHOW_ADULT_KEY, false)) {
                    adultSwitch.setOnCheckedChangeListener(null)
                    adultSwitch.setChecked(true)
                }

                adultSwitch.setOnCheckedChangeListener { _, isCheked ->
                    val editor = sharedPref.edit()
                    if (isCheked) {
                        editor.putBoolean(SHOW_ADULT_KEY, true)
                    } else {
                        editor.putBoolean(SHOW_ADULT_KEY, false)
                    }
                    editor.apply()
                }
            }
            val genresCode = sharedPref.getInt(SHOW_GENRES, 0x7FFFF)
            setCheckBox(genresCode)
        }

    }

    private fun setCheckBox(genresCode: Int) {

        with(binding){
            conditionCheck(genresCode, GENRE_ACTION_MOVIE, boxActionMovie)
            conditionCheck(genresCode, GENRE_ADVENTURES, boxAdventures)
            conditionCheck(genresCode, GENRE_CARTOONS, boxCartoons)
            conditionCheck(genresCode, GENRE_COMEDY, boxComedy)
            conditionCheck(genresCode, GENRE_CRIMINAL, boxCriminal)
            conditionCheck(genresCode, GENRE_DOCUMENTARY, boxDocumentary)
            conditionCheck(genresCode, GENRE_DRAMA, boxDrama)
            conditionCheck(genresCode, GENRE_FAMILY, boxFamilieMovie)
            conditionCheck(genresCode, GENRE_FANTASY, boxFantasy)
            conditionCheck(genresCode, GENRE_HISTORY, boxHistory)
            conditionCheck(genresCode, GENRE_HORROR, boxHorror)
            conditionCheck(genresCode, GENRE_MUSIC, boxMusic)
            conditionCheck(genresCode, GENRE_DETECTIVE, boxDetective)
            conditionCheck(genresCode, GENRE_MELODRAMA, boxMelodrama)
            conditionCheck(genresCode, GENRE_FANTASTIC, boxFantastic)
            conditionCheck(genresCode, GENRE_TELEFILM, boxTelefilm)
            conditionCheck(genresCode, GENRE_THRILLER, boxThriller)
            conditionCheck(genresCode, GENRE_MILITARY, boxMilitary)
            conditionCheck(genresCode, GENRE_WESTERN, boxWestern)
        }
    }

    private fun conditionCheck(genresCode: Int, genreToCheck: Int, checkBox: CheckBox) {
        if (((1 shl genreToCheck) and (genresCode)) != 0) {
            checkBox.setOnCheckedChangeListener(null)
            checkBox.setChecked(true)
        }
        else {
            checkBox.setOnCheckedChangeListener(null)
            checkBox.setChecked(false)
        }
    }

    override fun onStop() {
        with(binding) {
            val genresCode = checkBoxToInt(GENRE_ACTION_MOVIE, boxActionMovie) or
            checkBoxToInt(GENRE_ADVENTURES, boxAdventures) or
            checkBoxToInt(GENRE_CARTOONS, boxCartoons) or
            checkBoxToInt(GENRE_COMEDY, boxComedy) or
            checkBoxToInt(GENRE_CRIMINAL, boxCriminal) or
            checkBoxToInt(GENRE_DOCUMENTARY, boxDocumentary) or
            checkBoxToInt(GENRE_DRAMA, boxDrama) or
            checkBoxToInt(GENRE_FAMILY, boxFamilieMovie) or
            checkBoxToInt(GENRE_FANTASY, boxFantasy) or
            checkBoxToInt(GENRE_HISTORY, boxHistory) or
            checkBoxToInt(GENRE_HORROR, boxHorror) or
            checkBoxToInt(GENRE_MUSIC, boxMusic) or
            checkBoxToInt(GENRE_DETECTIVE, boxDetective) or
            checkBoxToInt(GENRE_MELODRAMA, boxMelodrama) or
            checkBoxToInt(GENRE_FANTASTIC, boxFantastic) or
            checkBoxToInt(GENRE_TELEFILM, boxTelefilm) or
            checkBoxToInt(GENRE_THRILLER, boxThriller) or
            checkBoxToInt(GENRE_MILITARY, boxMilitary) or
            checkBoxToInt(GENRE_WESTERN, boxWestern)

            activity?.let {
                val sharedPref = it.getPreferences(Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putInt(SHOW_GENRES, genresCode)
                editor.apply()
            }
        }
        super.onStop()
    }

    private fun checkBoxToInt(shift: Int, checkBox: CheckBox): Int {
        if (checkBox.isChecked) return (1 shl shift)
        else return 0
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }
}