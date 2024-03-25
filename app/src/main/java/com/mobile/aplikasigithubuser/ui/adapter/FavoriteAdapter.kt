package com.mobile.aplikasigithubuser.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mobile.aplikasigithubuser.database.FavoriteUser
import com.mobile.aplikasigithubuser.databinding.ItemUsersBinding
import com.mobile.aplikasigithubuser.ui.main.DetailUserActivity

class FavoriteAdapter : ListAdapter<FavoriteUser, FavoriteAdapter.FavoriteViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class FavoriteViewHolder(private val binding: ItemUsersBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val favoriteUser = getItem(position)
                val intent = Intent(v?.context, DetailUserActivity::class.java)
                intent.putExtra("username", favoriteUser.username)
                intent.putExtra("avatarUrl", favoriteUser.avatarUrl)
                v?.context?.startActivity(intent)
            }
        }

        fun bind(favoriteUser: FavoriteUser) {
            binding.itemName.text = favoriteUser.username

            Glide.with(binding.root)
                .load(favoriteUser.avatarUrl)
                .into(binding.profileImage)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteUser>() {
            override fun areItemsTheSame(oldItem: FavoriteUser, newItem: FavoriteUser): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: FavoriteUser, newItem: FavoriteUser): Boolean {
                return oldItem == newItem
            }
        }
    }
}