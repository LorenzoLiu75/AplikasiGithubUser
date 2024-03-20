package com.mobile.aplikasigithubuser.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.aplikasigithubuser.data.response.ItemsItem
import com.mobile.aplikasigithubuser.databinding.FragmentFollowBinding

class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private val followersViewModel by viewModels<FollowersViewModel>()
    private val followingViewModel by viewModels<FollowingViewModel>()

    private var position: Int = 0
    private var username: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME) ?: ""
        }

        if (position == 1) {
            followersViewModel.getFollowers(username)
            followersViewModel.followers.observe(viewLifecycleOwner) { followersList ->
                setupRecyclerView(followersList)
            }
        } else {
            followingViewModel.getFollowing(username)
            followingViewModel.following.observe(viewLifecycleOwner) { followingList ->
                setupRecyclerView(followingList)
            }
        }

        followersViewModel.errorText.observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage != null) {
                followersViewModel.setErrorText(null)
                showErrorDialog(errorMessage)
            }
        }

        followersViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        followingViewModel.errorText.observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage != null) {
                followingViewModel.setErrorText(null)
                showErrorDialog(errorMessage)
            }
        }

        followingViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun showErrorDialog(errorMessage: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Error")
            .setMessage(errorMessage)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun setupRecyclerView(userList: List<ItemsItem>?) {
        binding.listFollow.layoutManager = LinearLayoutManager(requireContext())

        val adapter = GithubAdapter()
        binding.listFollow.adapter = adapter

        userList?.let {
            adapter.submitList(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }

}