package com.example.coroutineapp.arch

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

import com.example.coroutineapp.models.GithubUser
import kotlinx.coroutines.*


import javax.inject.Inject


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
    val spinner = MutableLiveData<Boolean>()

    fun fetchFollowers(user: String){
        scope.launch(handler) {
            try {
                launch(Dispatchers.Main) { spinner.value = true }
                val followers = githubDataSource.getFollowersFromGithub(user)
                followersList.postValue(followers)
            } finally {
                launch(Dispatchers.Main) { spinner.value = false }
            }
        }
    }

    private fun cancelAllRequests() = job.cancel()

    override fun onCleared() {
        cancelAllRequests()
        super.onCleared()
    }
}
