package com.mobile.aplikasigithubuser.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.aplikasigithubuser.R
import com.mobile.aplikasigithubuser.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var githubAdapter: GithubAdapter
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        val query = searchView.text.toString()
                        mainViewModel.searchGithubUsers(query)
                        searchView.hide()
                        return@setOnEditorActionListener true
                    }
                    false
                }
        }

        setupRecyclerView()

        mainViewModel.githubUserList.observe(this) { githubUsers ->
            githubAdapter.submitList(githubUsers)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.errorText.observe(this) { errorMessage ->
            if (errorMessage != null) {
                mainViewModel.setErrorText(null)
                showErrorDialog(errorMessage)
            }
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View,
                                     menuInfo: ContextMenu.ContextMenuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.item_menu, menu)
    }


    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId) {
            R.id.menu_favorite -> {
                startActivity(Intent(this, ListFavoriteActivity::class.java))
                true
            }

            else -> super.onContextItemSelected(item)
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