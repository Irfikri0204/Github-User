package com.irfikri.githubuser.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.irfikri.githubuser.api.RetrofitClient
import com.irfikri.githubuser.data.model.User
import com.irfikri.githubuser.data.model.UserResponse
import com.irfikri.githubuser.ui.tema.TemaDatastore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: TemaDatastore): ViewModel() {
    fun getTema() = pref.getTheme().asLiveData()

    val listUser = MutableLiveData<ArrayList<User>>()
    fun setSearchUser(query:String){
        RetrofitClient.apiInstance
            .getSearchUsers(query)
            .enqueue(object: Callback<UserResponse>{
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if(response.isSuccessful){
                        listUser.postValue(response.body()?.items)
                    }
                }
                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }
    fun getSearchUsers():LiveData<ArrayList<User>>{
        return listUser
    }
    class Factory(private val pref: TemaDatastore): ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T = MainViewModel(pref) as T
    }
}