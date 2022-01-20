package ru.profitsw2000.moviecollectiondb.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.profitsw2000.moviecollectiondb.databinding.FragmentMainRecyclerItemBinding
import ru.profitsw2000.moviecollectiondb.model.representation.Category
import ru.profitsw2000.moviecollectiondb.model.representation.Movie
import ru.profitsw2000.moviecollectiondb.ui.main.MainFragment

class ParentAdapter (private val categories: List<Category>, private val itemClickListener: MainFragment.OnItemViewClickListener)
    : RecyclerView.Adapter<ParentAdapter.MainViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()
    private var categoriesData: List<Category> = listOf()
    private lateinit var binding: FragmentMainRecyclerItemBinding

    @SuppressLint("NotifyDataSetChanged")
    fun setCategories(data: List<Category>) {
        categoriesData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        binding = FragmentMainRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MainViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount() = categories.size

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(category: Category) = with(binding) {
            categoryTitle.text = category.title
            movieRecyclerView.apply {
                layoutManager = LinearLayoutManager(movieRecyclerView.context, LinearLayoutManager.HORIZONTAL, false)
                adapter = ChildAdapter(category.movieList, itemClickListener)
                setRecycledViewPool(viewPool)
            }
        }
    }
}
