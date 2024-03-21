package com.mobile.aplikasigithubuser.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.mobile.aplikasigithubuser.database.FavoriteUser
import com.mobile.aplikasigithubuser.database.FavoriteUserDao
import com.mobile.aplikasigithubuser.database.FavoriteUserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {

    private val mFavoriteUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserRoomDatabase.getDatabase(application)
        mFavoriteUserDao = db.favoriteUserDao()
    }

    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>> = mFavoriteUserDao.getAllFavoriteUsers()

    fun insert(favoriteUser: FavoriteUser) {
        executorService.execute { mFavoriteUserDao.insert(favoriteUser) }
    }

    fun delete(favoriteUser: FavoriteUser) {
        executorService.execute { mFavoriteUserDao.delete(favoriteUser) }
    }

    fun checkIfUserIsFavorite(username: String): LiveData<Boolean> {
        return mFavoriteUserDao.checkIfUserIsFavorite(username)
    }
}