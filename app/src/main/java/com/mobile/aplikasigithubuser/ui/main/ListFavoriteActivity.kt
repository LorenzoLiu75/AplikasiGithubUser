package com.mobile.aplikasigithubuser.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.aplikasigithubuser.databinding.ActivityListFavoriteBinding
import com.mobile.aplikasigithubuser.helper.ViewModelFactory

class ListFavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListFavoriteBinding
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = ""

        binding.listFavorite.layoutManager = LinearLayoutManager(this)
        favoriteAdapter = FavoriteAdapter()
        binding.listFavorite.adapter = favoriteAdapter

        val factory = ViewModelFactory.getInstance(application)
        favoriteViewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]

        favoriteViewModel.getAllFavoriteUsers().observe(this) { favoriteUsers ->
            favoriteAdapter.submitList(favoriteUsers)
        }

        favoriteViewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}