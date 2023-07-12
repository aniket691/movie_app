package com.example.movietask3.presentation.ui.fragment.favourites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movietask3.presentation.ui.adpaters.MovieAdapter
import com.example.movietask3.api.RetrofitInstance
import com.example.movietask3.databinding.FragmentFavouritesBinding
import com.example.movietask3.domain.db.MovieDatabase
import com.example.movietask3.domain.repository.MovieRepository
import com.example.movietask3.presentation.ui.viewmodels.MovieViewModel
import com.example.movietask3.presentation.ui.viewmodels.MovieViewModelFactory

class FavouritesFragment : Fragment() {

    private var _binding: FragmentFavouritesBinding? = null

    private val binding get() = _binding!!

    //viewModel
    private lateinit var viewModel: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val movieService = RetrofitInstance.api
        val md = MovieDatabase(requireContext()).getMovieDao()
        val repository = MovieRepository(md, movieService)

        //viewModel object
        viewModel = ViewModelProvider(
            this,
            MovieViewModelFactory(requireContext(), repository)
        )[MovieViewModel::class.java]

        //getting favourite movies from database to fetch in recycler view
        viewModel.getAllFavouriteDatabase().observe(viewLifecycleOwner) { value ->
            Log.d("TAG: ==>", value.toString())
            val movieAdapter = MovieAdapter(requireContext(), viewModel)
            binding.recyclerViewFavourites.adapter = movieAdapter
            binding.recyclerViewFavourites.layoutManager = LinearLayoutManager(requireContext())
            movieAdapter.differ.submitList(value)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}