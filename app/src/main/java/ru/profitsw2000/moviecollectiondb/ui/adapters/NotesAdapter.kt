package ru.profitsw2000.moviecollectiondb.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.profitsw2000.moviecollectiondb.R
import ru.profitsw2000.moviecollectiondb.databinding.FragmentNoteBinding
import ru.profitsw2000.moviecollectiondb.databinding.FragmentNoteRecyclerItemBinding
import ru.profitsw2000.moviecollectiondb.databinding.HorizontalRecyclerItemBinding
import ru.profitsw2000.moviecollectiondb.room.NoteEntity

class NotesAdapter: RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    private var data: List<NoteEntity> = arrayListOf()
    private lateinit var binding: FragmentNoteRecyclerItemBinding

    fun setData(data: List<NoteEntity>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = FragmentNoteRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(note: NoteEntity) {
            with(binding){
                movieTitle.text = note.title + " (" + note.id.toString() + ")"
                movieNote.text = note.movieNote
            }
        }
    }
}