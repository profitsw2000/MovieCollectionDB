package ru.profitsw2000.moviecollectiondb.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.profitsw2000.moviecollectiondb.databinding.FragmentFavoriteRecyclerItemBinding
import ru.profitsw2000.moviecollectiondb.databinding.FragmentNoteRecyclerItemBinding
import ru.profitsw2000.moviecollectiondb.room.FavoriteEntity
import ru.profitsw2000.moviecollectiondb.room.NoteEntity

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private var data: List<FavoriteEntity> = arrayListOf()
    private lateinit var binding: FragmentFavoriteRecyclerItemBinding

    fun setData(data: List<FavoriteEntity>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = FragmentFavoriteRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        fun bind(favorite: FavoriteEntity) {
            with(binding){
                movieTitle.text = favorite.title
            }
        }
    }
}