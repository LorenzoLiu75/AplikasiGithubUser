package com.mobile.aplikasigithubuser.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobile.aplikasigithubuser.data.repository.FavoriteUserRepository
import com.mobile.aplikasigithubuser.database.FavoriteUser

class FavoriteViewModel(private val repository: FavoriteUserRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>> {
        return repository.getAllFavoriteUsers()
    }

}