package com.mobile.aplikasigithubuser.helper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobile.aplikasigithubuser.ui.main.SettingViewModel

class SettingViewModelFactory(private val pref: SettingPreferences): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}