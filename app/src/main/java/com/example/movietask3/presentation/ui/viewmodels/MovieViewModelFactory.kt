package com.example.movietask3.presentation.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movietask3.domain.repository.MovieRepository


class MovieViewModelFactory(private val application: Context, private val repository: MovieRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieViewModel(application,repository) as T
    }
}