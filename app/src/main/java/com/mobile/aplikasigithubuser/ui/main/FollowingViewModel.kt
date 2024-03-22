package com.mobile.aplikasigithubuser.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobile.aplikasigithubuser.data.response.ItemsItem
import com.mobile.aplikasigithubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {
    private val _following = MutableLiveData<List<ItemsItem>?>()
    val following: LiveData<List<ItemsItem>?> = _following

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorText = MutableLiveData<String?>()
    val errorText: LiveData<String?> = _errorText

    fun setErrorText(errorMessage: String?) {
        _errorText.value = errorMessage
    }

    fun getFollowing(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val followingList = response.body()
                    _following.value = followingList
                } else {
                    setErrorText("Gagal mengambil data following")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                setErrorText("Gagal terhubung ke server. Mohon periksa koneksi internet anda")
            }
        })
    }
}