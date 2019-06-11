package com.example.coroutineapp.utils


import com.example.coroutineapp.models.GithubUser
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {

    @GET("users/{user}/followers")
    suspend fun getFollowers(@Path("user") user: String): Response<List<GithubUser>>
}
