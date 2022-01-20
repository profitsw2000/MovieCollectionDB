package ru.profitsw2000.moviecollectiondb.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.profitsw2000.moviecollectiondb.R
import ru.profitsw2000.moviecollectiondb.databinding.FragmentMainRecyclerItemBinding
import ru.profitsw2000.moviecollectiondb.databinding.HorizontalRecyclerItemBinding
import ru.profitsw2000.moviecollectiondb.model.representation.Category
import ru.profitsw2000.moviecollectiondb.model.representation.Movie
import ru.profitsw2000.moviecollectiondb.ui.main.MainFragment

class ChildAdapter(private val movies : List<Movie>, private val itemClickListener: MainFragment.OnItemViewClickListener)
    : RecyclerView.Adapter<ChildAdapter.ViewHolder>(){

    private lateinit var binding: HorizontalRecyclerItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = HorizontalRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding.root)
    }

/*    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =  LayoutInflater.from(parent.context)
            .inflate(R.layout.horizontal_recycler_item,parent,false)
        return ViewHolder(v)
    }*/

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
/*        val movie = movies[position]
        holder.image.setImageResource(movie.picture)
        holder.horizontalRecyclerItemTextView.text = movie.title*/
        with(binding) {
            root.setOnClickListener { itemClickListener.onItemViewClick(movie) }
        }
    }


    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){

        fun bind(movie: Movie) = with(binding) {
            horizontalRecyclerItemTextView.text = movie.title
            movieDescriptionImage.setImageResource(movie.picture)
/*            movieRecyclerView.apply {
                layoutManager = LinearLayoutManager(movieRecyclerView.context, LinearLayoutManager.HORIZONTAL, false)
                adapter = ChildAdapter(category.movieList, itemClickListener)
                setRecycledViewPool(viewPool)
            }*/
        }
/*        with(binding) {
            val titleTextView : TextView = movieTitle
            val image : ImageView = movieDescriptionImage
        }*/
    }
}