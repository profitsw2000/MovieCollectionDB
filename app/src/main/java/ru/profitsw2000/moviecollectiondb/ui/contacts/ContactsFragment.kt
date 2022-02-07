package ru.profitsw2000.moviecollectiondb.ui.contacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.profitsw2000.moviecollectiondb.R
import ru.profitsw2000.moviecollectiondb.databinding.FragmentContactsBinding
import ru.profitsw2000.moviecollectiondb.databinding.FragmentFavoriteBinding
import ru.profitsw2000.moviecollectiondb.ui.adapters.FavoriteAdapter

class ContactsFragment : Fragment() {
    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!
    //private val adapter: FavoriteAdapter by lazy { FavoriteAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = ContactsFragment()
    }
}