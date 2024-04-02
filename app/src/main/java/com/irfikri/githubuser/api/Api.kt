package com.irfikri.githubuser.api

import com.irfikri.githubuser.data.model.DetailUserResponse
import com.irfikri.githubuser.data.model.User
import com.irfikri.githubuser.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username:String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}