package com.mobile.aplikasigithubuser.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobile.aplikasigithubuser.data.repository.FavoriteUserAddUpdateViewModel
import com.mobile.aplikasigithubuser.data.repository.FavoriteUserRepository
import com.mobile.aplikasigithubuser.ui.main.DetailUserViewModel
import com.mobile.aplikasigithubuser.ui.main.FavoriteViewModel

class ViewModelFactory(private val mApplication: Application) : ViewModelProvider.Factory {

    private val favoriteUserRepository: FavoriteUserRepository by lazy {
        FavoriteUserRepository(mApplication)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailUserViewModel::class.java)) {
            return DetailUserViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(FavoriteUserAddUpdateViewModel::class.java)) {
            return FavoriteUserAddUpdateViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(favoriteUserRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}