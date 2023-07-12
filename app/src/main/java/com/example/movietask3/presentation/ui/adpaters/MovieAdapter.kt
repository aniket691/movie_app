package com.example.movietask3.presentation.ui.adpaters


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movietask3.databinding.ItemArticlePreviewsBinding
import com.example.movietask3.domain.model.Movie
import com.example.movietask3.presentation.ui.viewmodels.MovieViewModel


class MovieAdapter(
    private val context: Context,
    private val viewModel: MovieViewModel
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var movieList = emptyList<Movie>()

    inner class MovieViewHolder(val binding: ItemArticlePreviewsBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.imdb_id == newItem.imdb_id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            ItemArticlePreviewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = differ.currentList[position]
        with(holder) {
            binding.mvTitle.text = movie.title
            binding.buttonDel.setOnClickListener {
                viewModel.deleteMovie(movie)
                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
            }

            binding.buttonFav.setOnClickListener {
                viewModel.addToFav(movieList[position].imdb_id)
                Toast.makeText(context, "Added to Favourites", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun addItems(newItems: List<Movie>) {
        val currentList = differ.currentList.toMutableList()
        currentList.addAll(newItems)
        differ.submitList(currentList)
    }

}

