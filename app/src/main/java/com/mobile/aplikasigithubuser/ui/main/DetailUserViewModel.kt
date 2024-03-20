package com.mobile.aplikasigithubuser.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobile.aplikasigithubuser.data.response.DetailUserResponse
import com.mobile.aplikasigithubuser.data.response.ItemsItem
import com.mobile.aplikasigithubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel : ViewModel() {

    private val _detailUser = MutableLiveData<DetailUserResponse?>()
    val detailuser: LiveData<DetailUserResponse?> = _detailUser

    private val _following = MutableLiveData<List<ItemsItem>?>()
    val following: LiveData<List<ItemsItem>?> = _following

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _username = MutableLiveData<String?>()
    val username: LiveData<String?> = _username

    private val _errorText = MutableLiveData<String?>()
    val errorText: LiveData<String?> = _errorText

    fun setUsername(username: String?) {
        _username.value = username
    }

    fun setErrorText(errorMessage: String?) {
        _errorText.value = errorMessage
    }

    fun showDetailUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>, response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _detailUser.value = responseBody
                } else {
                    setErrorText("Gagal mengambil data user")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                setErrorText("Gagal terhubung ke server. Mohon periksa koneksi internet anda")
            }
        })
    }
}