package ru.profitsw2000.moviecollectiondb.ui.menu

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import ru.profitsw2000.moviecollectiondb.R
import org.koin.android.ext.android.inject
import ru.profitsw2000.moviecollectiondb.databinding.FragmentDescriptionBinding
import ru.profitsw2000.moviecollectiondb.databinding.FragmentSettingsBinding

const val SHOW_ADULT_KEY = "Show_adult"

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
        }
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