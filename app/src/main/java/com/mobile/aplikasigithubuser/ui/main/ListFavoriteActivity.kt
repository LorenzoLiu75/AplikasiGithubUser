package com.mobile.aplikasigithubuser.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.aplikasigithubuser.databinding.ActivityListFavoriteBinding
import com.mobile.aplikasigithubuser.helper.FavoriteViewModelFactory
import com.mobile.aplikasigithubuser.ui.adapter.FavoriteAdapter

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

        val factory = FavoriteViewModelFactory.getInstance(application)
        favoriteViewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]

        favoriteViewModel.getAllFavoriteUsers().observe(this) { favoriteUsers ->
            favoriteAdapter.submitList(favoriteUsers)
        }
    }
}