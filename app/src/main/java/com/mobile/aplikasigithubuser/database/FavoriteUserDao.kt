package com.mobile.aplikasigithubuser.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavoriteUserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteUser: FavoriteUser)

    @Delete
    fun delete(favoriteUser: FavoriteUser)

    @Query("SELECT EXISTS(SELECT 1 FROM FavoriteUser WHERE username = :username LIMIT 1)")
    fun checkIfUserIsFavorite(username: String): LiveData<Boolean>

    @Query("SELECT * FROM FavoriteUser")
    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>>
}