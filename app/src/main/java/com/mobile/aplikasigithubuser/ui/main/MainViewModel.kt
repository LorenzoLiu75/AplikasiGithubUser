package com.mobile.aplikasigithubuser.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobile.aplikasigithubuser.data.response.GithubResponse
import com.mobile.aplikasigithubuser.data.response.ItemsItem
import com.mobile.aplikasigithubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _githubUserList = MutableLiveData<List<ItemsItem>?>()
    val githubUserList: LiveData<List<ItemsItem>?> = _githubUserList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorText = MutableLiveData<String?>()
    val errorText: LiveData<String?> = _errorText


    init {
        findGithubUser(query = "lorenzo")
    }

    fun setErrorText(errorMessage: String?) {
        _errorText.value = errorMessage
    }

    private fun findGithubUser(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsers(query)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _githubUserList.value = responseBody.items?.filterNotNull()
                    }
                } else {
                    setErrorText("Gagal mengambil data user")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                setErrorText("Gagal terhubung ke server. Mohon periksa koneksi internet anda")
            }
        })
    }

    fun searchGithubUsers(query: String) {
        if (query.isNotBlank()) {
            findGithubUser(query)
        }
    }
}