package com.irfikri.githubuser.ui.tema

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TemaViewModel(private val pref: TemaDatastore): ViewModel() {
    fun getTema() = pref.getTheme().asLiveData()
    fun saveTema(isDark:Boolean) {
        viewModelScope.launch {
            pref.saveTheme(isDark)
        }
    }
    class Factory(private val pref: TemaDatastore):ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T = TemaViewModel(pref) as T
    }
}