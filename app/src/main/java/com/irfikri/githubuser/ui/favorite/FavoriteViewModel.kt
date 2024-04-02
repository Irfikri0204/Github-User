package com.irfikri.githubuser.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.irfikri.githubuser.data.local.FavoriteUser
import com.irfikri.githubuser.data.local.FavoriteUserDAO
import com.irfikri.githubuser.data.local.UserDatabase

class FavoriteViewModel(application: Application):AndroidViewModel(application) {
    private var userDao: FavoriteUserDAO?
    private var userDb: UserDatabase?

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

    fun getFavoriteUser():LiveData<List<FavoriteUser>>?{
        return userDao?.getFavoriteUser()
    }
}