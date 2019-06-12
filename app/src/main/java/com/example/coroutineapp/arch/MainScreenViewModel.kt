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

    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.IO
    private val scope = CoroutineScope(coroutineContext)
    private val handler = CoroutineExceptionHandler { _, exception ->
        println("Caught $exception")
    }

    val followersList = MutableLiveData<MutableList<GithubUser>>()

    fun fetchFollowers(){
        scope.launch(handler) {
            try {
                val followers = githubDataSource.getFollowersFromGithub("JakeWharton")
                followersList.postValue(followers)
            } finally {
                println("finally block is running")
            }

        }
    }

    fun cancelAllRequests() = coroutineContext.cancel()
}
