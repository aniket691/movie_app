package com.example.movietask3.presentation.ui.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movietask3.presentation.ui.adpaters.MovieAdapter
import com.example.movietask3.api.RetrofitInstance
import com.example.movietask3.common.Resource
import com.example.movietask3.data.remote.dto.toMovieList
import com.example.movietask3.databinding.FragmentHomeBinding
import com.example.movietask3.domain.db.MovieDatabase
import com.example.movietask3.domain.repository.MovieRepository
import com.example.movietask3.presentation.ui.viewmodels.MovieViewModel
import com.example.movietask3.presentation.ui.viewmodels.MovieViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    //binding
    private val binding get() = _binding!!

    //viewModel
    private lateinit var viewModel: MovieViewModel

    // movie adapter
    lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val movieService = RetrofitInstance.api
        val md = MovieDatabase(requireContext()).getMovieDao()
        val repository = MovieRepository(md, movieService)
        viewModel = ViewModelProvider(
            this,
            MovieViewModelFactory(requireContext(), repository)
        )[MovieViewModel::class.java]

        setupRecyclerView()

        //using viewModel so that when the data in the database is empty then we can again fetch from api
        viewModel.getAllMoviesDatabase().observe(viewLifecycleOwner) { value ->
            Log.d("TAG: ==>", value.toString())
            if (value.isEmpty()) {
                //fetch the data from the api and save in the database
                viewModel.getAllMovies("1.json")
            }
        }
        //fetching data from database
        viewModel.getAllMoviesDatabase().observe(viewLifecycleOwner) { value ->
            Log.d("TAG: ==>", value.toString())
            movieAdapter.differ.submitList(value)
        }

        //when reaching on end recycler view we will fetch new data
        binding.recyclerViewHome.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!recyclerView.canScrollVertically(1) && dy > 0) {
                    //scrolled to BOTTOM
                    Toast.makeText(requireContext(), "Scrolled to Bottom", Toast.LENGTH_SHORT)
                        .show()

                    viewModel.getMoviesFromApi("2.json")

                    viewModel.moviesApiData.observe(viewLifecycleOwner, Observer { response ->
                        when (response) {
                            // check if success
                            is Resource.Success -> {
                                response.data?.let { movieResponse ->
                                    movieAdapter.addItems(movieResponse.toMovieList())
                                    Log.d("TAG", "List Size == >: ${movieResponse.movieList.size}")
                                }
                            }
                            //check error
                            is Resource.Error -> {
                                response.message?.let { message ->
                                    Log.d("TAG", "An error occurred: $message")
                                }
                            }
                            //check loading
                            is Resource.Loading -> {
                                Log.d("TAG", "Loading")

                            }
                        }
                    })

                } else if (!recyclerView.canScrollVertically(-1) && dy < 0) {
                    //scrolled to TOP
                    Toast.makeText(requireContext(), "Scrolled to TOP", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
        return root
    }

    private fun setupRecyclerView() {
        //setting up recycler view and movie data
        movieAdapter = MovieAdapter(requireContext(), viewModel)
        binding.recyclerViewHome.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}