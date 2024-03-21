package com.mobile.aplikasigithubuser.data.repository

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mobile.aplikasigithubuser.database.FavoriteUser

class FavoriteUserAddUpdateViewModel(application: Application) : AndroidViewModel(application) {
    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    fun insert(favoriteUser: FavoriteUser) {
        mFavoriteUserRepository.insert(favoriteUser)
    }

    fun delete(favoriteUser: FavoriteUser) {
        mFavoriteUserRepository.delete(favoriteUser)
    }
}