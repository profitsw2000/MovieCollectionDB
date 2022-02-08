package ru.profitsw2000.moviecollectiondb.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.profitsw2000.moviecollectiondb.databinding.FragmentContactsRecyclerItemBinding
import ru.profitsw2000.moviecollectiondb.model.representation.Contact
import ru.profitsw2000.moviecollectiondb.ui.contacts.ContactsFragment
import ru.profitsw2000.moviecollectiondb.ui.main.MainFragment

class ContactsAdapter (private val itemClickListener: ContactsFragment.OnItemViewClickListener)
    : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    private var data: List<Contact> = arrayListOf()
    private lateinit var binding: FragmentContactsRecyclerItemBinding

    fun setData(data: List<Contact>) {
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
        fun bind(contact: Contact) {
            with(binding){
                contactName.text = contact.name
                contactPhoneNumber.text = contact.phone_number[0]
                root.setOnClickListener { itemClickListener.onItemViewClick(contact.phone_number[0]) }
            }
        }
    }
}