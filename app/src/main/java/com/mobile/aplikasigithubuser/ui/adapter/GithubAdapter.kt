package com.mobile.aplikasigithubuser.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mobile.aplikasigithubuser.data.response.ItemsItem
import com.mobile.aplikasigithubuser.databinding.ItemUsersBinding
import com.mobile.aplikasigithubuser.ui.main.DetailUserActivity

class GithubAdapter : ListAdapter<ItemsItem, GithubAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val githubUser = getItem(position)
        holder.bind(githubUser)
        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailUserActivity::class.java)
            intentDetail.putExtra("username", githubUser.login)
            intentDetail.putExtra("avatarUrl", githubUser.avatarUrl)
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    class MyViewHolder(private val binding: ItemUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemsItem) {
            binding.itemName.text = item.login

            Glide.with(binding.root)
                .load(item.avatarUrl)
                .into(binding.profileImage)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}

