package com.example.movietask3.presentation.ui.adpaters


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movietask3.R
import com.example.movietask3.databinding.ItemArticlePreviewsBinding
import com.example.movietask3.domain.model.Movie
import com.example.movietask3.presentation.ui.viewmodels.MovieViewModel


class MovieAdapter(
    private val context: Context,
    private val viewModel: MovieViewModel
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

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

            binding.movieTitleTv.text = movie.title
            binding.movieDescTv.text = movie.short_summery
            binding.year.text = movie.year.toString()
            binding.runtime.text = movie.runtime.toString()
            binding.cast.text = movie.cast

            Glide
                .with(itemView.context)
                .load(movie.movie_poster)
                .centerCrop()
                .placeholder(R.drawable.profile_pic)
                .into(binding.posterIv);

            binding.buttonDel.setOnClickListener {
                viewModel.deleteMovie(movie)
                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
            }

            binding.buttonFav.setOnClickListener {
                viewModel.addToFav(differ.currentList[position].imdb_id)
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

