package com.mobile.aplikasigithubuser.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mobile.aplikasigithubuser.R
import com.mobile.aplikasigithubuser.data.repository.FavoriteUserAddUpdateViewModel
import com.mobile.aplikasigithubuser.data.response.DetailUserResponse
import com.mobile.aplikasigithubuser.database.FavoriteUser
import com.mobile.aplikasigithubuser.databinding.ActivityDetailUserBinding

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private val userDetailViewModel by viewModels<DetailUserViewModel>()
    private val favoriteUserAddUpdateViewModel by viewModels<FavoriteUserAddUpdateViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        val username = intent.getStringExtra("username")

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username.orEmpty()

        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.follower)
                1 -> getString(R.string.following)
                else -> ""
            }
        }.attach()

        if (userDetailViewModel.username.value == null) {
            userDetailViewModel.showDetailUser(username.orEmpty())
            userDetailViewModel.setUsername(username)
        }

        userDetailViewModel.detailuser.observe(this) { detailUser ->
            if (detailUser != null) {
                bindUserData(detailUser)
            }
        }

        userDetailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        userDetailViewModel.errorText.observe(this) { errorMessage ->
            if (errorMessage != null) {
                userDetailViewModel.setErrorText(null)
                showErrorDialog(errorMessage)
            }
        }

        binding.fab.setOnClickListener {
            saveFavoriteUserToDatabase()
        }
    }

    private fun bindUserData(detailUser: DetailUserResponse) {
        binding.displayName.text = detailUser.name
        binding.userName.text = detailUser.login
        binding.followersCount.text = "${detailUser.followers} Followers"
        binding.followingCount.text = "${detailUser.following} Following"
        Glide.with(this)
            .load(detailUser.avatarUrl)
            .into(binding.profileImage)
    }

    private fun showErrorDialog(errorMessage: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
            .setMessage(errorMessage)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun saveFavoriteUserToDatabase() {
        val username = intent.getStringExtra("username") ?: return
        val avatarUrl = intent.getStringExtra("avatarUrl")

        val favoriteUser = FavoriteUser(username = username, avatarUrl = avatarUrl)
        favoriteUserAddUpdateViewModel.insert(favoriteUser)

        Toast.makeText(this, "Berhasil menambahkan user ke favorite", Toast.LENGTH_SHORT).show()
    }

}