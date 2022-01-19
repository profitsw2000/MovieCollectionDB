package ru.profitsw2000.moviecollectiondb.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.profitsw2000.moviecollectiondb.R
import ru.profitsw2000.moviecollectiondb.databinding.HorizontalRecyclerItemBinding
import ru.profitsw2000.moviecollectiondb.model.representation.Movie
import ru.profitsw2000.moviecollectiondb.ui.main.MainFragment

class ChildAdapter(private val movies : List<Movie>, private val itemClickListener: MainFragment.OnItemViewClickListener)
    : RecyclerView.Adapter<ChildAdapter.ViewHolder>(){

    private lateinit var binding: HorizontalRecyclerItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =  LayoutInflater.from(parent.context)
            .inflate(R.layout.horizontal_recycler_item,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
/*        holder.imageView.setImageResource(child.image)
        holder.textView.text = movie.title*/
    }


    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){

        fun bind(movie: Movie) = with(binding) {
            horizontalRecyclerItemTextView.text = movie.title
            movieDescriptionImage.setImageResource(movie.picture)
            root.setOnClickListener { itemClickListener.onItemViewClick(movie) }
        }

    }
}