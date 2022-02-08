package ru.profitsw2000.moviecollectiondb.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.profitsw2000.moviecollectiondb.databinding.FragmentContactsRecyclerItemBinding
import ru.profitsw2000.moviecollectiondb.databinding.FragmentFavoriteRecyclerItemBinding
import ru.profitsw2000.moviecollectiondb.model.representation.Contacts
import ru.profitsw2000.moviecollectiondb.room.FavoriteEntity

class ContactsAdapter : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    private var data: List<Contacts> = arrayListOf()
    private lateinit var binding: FragmentContactsRecyclerItemBinding

    fun setData(data: List<Contacts>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = FragmentContactsRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(contacts: Contacts) {
            with(binding){
                contactName.text = contacts.name
                contactPhoneNumber.text = contacts.phone_number[0]
            }
        }
    }
}