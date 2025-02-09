package com.irfikri.githubuser.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.irfikri.githubuser.api.RetrofitClient
import com.irfikri.githubuser.data.local.FavoriteUser
import com.irfikri.githubuser.data.local.FavoriteUserDAO
import com.irfikri.githubuser.data.local.UserDatabase
import com.irfikri.githubuser.data.model.DetailUserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {
    val user = MutableLiveData<DetailUserResponse>()
    private var userDao: FavoriteUserDAO?
    private var userDb: UserDatabase?

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

    fun setUserDetail(username: String) {
        RetrofitClient.apiInstance
            .getUserDetail(username)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
//                    Log.d("Failure", t.message)
                }
            })
    }

    fun getUserDetail(): LiveData<DetailUserResponse> {
        return user
    }
    fun addToFavorite(username: String,id:Int,avatar_url:String){
        CoroutineScope(Dispatchers.IO).launch {
            var user = FavoriteUser(
                username,
                id,
                avatar_url
            )
            userDao?.addToFavorite(user)
        }
    }
    suspend fun checkedUser(id: Int)=userDao?.checkUser(id)

    fun removeFromFavorite(id:Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavorite(id)
        }
    }

}