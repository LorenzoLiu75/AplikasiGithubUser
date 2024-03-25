package com.mobile.aplikasigithubuser.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.aplikasigithubuser.R
import com.mobile.aplikasigithubuser.databinding.ActivityMainBinding
import com.mobile.aplikasigithubuser.helper.EspressoIdlingResource
import com.mobile.aplikasigithubuser.helper.SettingPreferences
import com.mobile.aplikasigithubuser.helper.SettingViewModelFactory
import com.mobile.aplikasigithubuser.helper.dataStore
import com.mobile.aplikasigithubuser.ui.adapter.GithubAdapter


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var githubAdapter: GithubAdapter
    private val mainViewModel by viewModels<MainViewModel>()
    private val settingViewModel: SettingViewModel by viewModels {
        SettingViewModelFactory(SettingPreferences.getInstance(application.dataStore))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        val query = searchView.text.toString()
                        EspressoIdlingResource.increment()
                        mainViewModel.searchGithubUsers(query)
                        searchView.hide()
                        return@setOnEditorActionListener true
                    }
                    false
                }

            searchBar.setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener,
                Toolbar.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem): Boolean {
                    return when (item.itemId) {
                        R.id.menu_favorite -> {
                            startActivity(
                                Intent(
                                    this@MainActivity,
                                    ListFavoriteActivity::class.java
                                )
                            )
                            true
                        }
                        R.id.menu_setting -> {
                            startActivity(
                                Intent(
                                    this@MainActivity,
                                    SettingLightDarkModeActivity::class.java
                                )
                            )
                            true
                        }
                        else -> {
                            false
                        }
                    }
                }
            })
        }

        setupRecyclerView()

        mainViewModel.githubUserList.observe(this) { githubUsers ->
            if (!EspressoIdlingResource.idlingResource.isIdleNow){
                EspressoIdlingResource.decrement()
            }
            githubAdapter.submitList(githubUsers)
        }

        mainViewModel.isLoading.observe(this) {
            if (!EspressoIdlingResource.idlingResource.isIdleNow){
                EspressoIdlingResource.decrement()
            }
            showLoading(it)
        }

        mainViewModel.errorText.observe(this) { errorMessage ->
            if (!EspressoIdlingResource.idlingResource.isIdleNow){
                EspressoIdlingResource.decrement()
            }
            if (errorMessage != null) {
                mainViewModel.setErrorText(null)
                showErrorDialog(errorMessage)
            }
        }
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

    private fun setupRecyclerView() {
        githubAdapter = GithubAdapter()
        binding.listUsers.adapter = githubAdapter
        binding.listUsers.layoutManager = LinearLayoutManager(this)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}