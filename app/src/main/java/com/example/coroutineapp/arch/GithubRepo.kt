package com.example.coroutineapp.arch

import com.example.coroutineapp.models.GithubUser
import com.example.coroutineapp.utils.GithubService

import javax.inject.Inject

class GithubRepo
@Inject
internal constructor(private val githubService: GithubService): BaseRepo() {
    private val TAG = "GithubRepository"
    private var followers: List<GithubUser> = arrayListOf()

    suspend fun getFollowersFromGithub(user: String): MutableList<GithubUser>? {
        val githubResponse = apiCall(
            call = {githubService.getFollowers(user)},
            errorMessage = "Error fetching followers"
        )
        return githubResponse?.toMutableList()
    }

}
