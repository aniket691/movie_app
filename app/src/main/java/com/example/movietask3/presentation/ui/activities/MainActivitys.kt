package com.example.movietask3.presentation.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.movietask3.databinding.ActivityMainsBinding
import com.example.movietask3.presentation.ui.viewmodels.MovieViewModel

class MainActivitys : AppCompatActivity() {


    private lateinit var binding: ActivityMainsBinding

    private lateinit var viewModel: MovieViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}