package com.irfikri.githubuser.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.irfikri.githubuser.api.RetrofitClient
import com.irfikri.githubuser.data.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel:ViewModel (){
    val listFollowers = MutableLiveData<ArrayList<User>>()

    fun setListFollowers(username:String){
        RetrofitClient.apiInstance
            .getUserFollowers(username)
            .enqueue(object : Callback<ArrayList<User>>{
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if(response.isSuccessful){
                        listFollowers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {

                }

            })
    }
    fun getListFollowers():LiveData<ArrayList<User>>{
        return listFollowers
    }


}