package com.example.coroutineapp.arch

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.coroutineapp.models.GithubUser
import kotlinx.coroutines.*


import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MainScreenViewModel
@Inject
internal constructor(private val githubDataSource: GithubRepo) : ViewModel() {

    // Coroutine
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)
    private val handler = CoroutineExceptionHandler { _, exception ->
        println("Caught $exception")
    }

    // Data
    val followersList = MutableLiveData<MutableList<GithubUser>>()

    fun fetchFollowers(user: String){
        scope.launch(handler) {
            try {
                val followers = githubDataSource.getFollowersFromGithub(user)
                followersList.postValue(followers)
            } finally {
                //println("finally block is running")
            }

        }
    }

    fun cancelAllRequests() = job.cancel()

    override fun onCleared() {
        cancelAllRequests()
        super.onCleared()
    }
}
