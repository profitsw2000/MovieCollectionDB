package ru.profitsw2000.moviecollectiondb.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.profitsw2000.moviecollectiondb.databinding.FragmentMainRecyclerItemBinding
import ru.profitsw2000.moviecollectiondb.model.representation.Movie
import ru.profitsw2000.moviecollectiondb.ui.main.MainFragment

class ParentAdapter (private val itemClickListener: MainFragment.OnItemViewClickListener)
    : RecyclerView.Adapter<ParentAdapter.MainViewHolder>() {
    private var moviesData: List<Movie> = listOf()
    private lateinit var binding: FragmentMainRecyclerItemBinding

    @SuppressLint("NotifyDataSetChanged")
    fun setMovie(data: List<Movie>) {
        moviesData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        binding = FragmentMainRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MainViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(moviesData[position])
    }

    override fun getItemCount() = moviesData.size

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie) = with(binding) {
            mainFragmentRecyclerItemTextView.text = movie.title
            movieDescriptionImage.setImageResource(movie.picture)
            root.setOnClickListener { itemClickListener.onItemViewClick(movie) }
        }
    }
}
