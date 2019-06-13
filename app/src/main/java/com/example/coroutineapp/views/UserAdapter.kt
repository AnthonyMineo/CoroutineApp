package com.example.coroutineapp.views

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.coroutineapp.R
import com.example.coroutineapp.models.GithubUser
import kotlinx.coroutines.*

import java.util.ArrayList
import kotlin.coroutines.CoroutineContext

class UserAdapter : RecyclerView.Adapter<UserViewHolder>() {

    // Data
    private var users: List<GithubUser>? = null

    // Coroutine
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Default + job)
    private val handler = CoroutineExceptionHandler { _, exception ->
        println("Caught $exception")
    }

    init {
        this.users = ArrayList()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): UserViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val view = inflater.inflate(R.layout.user_recycle_item, viewGroup, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(userViewHolder: UserViewHolder, i: Int) {
        userViewHolder.updateWithUser(this.users!![i])
    }

    override fun getItemCount(): Int {
        return this.users!!.size
    }

    fun updateData(users: List<GithubUser>) {
        scope.launch(handler) {
            try {
                val diffResult = DiffUtil.calculateDiff(UserListDiffCallback(this@UserAdapter.users!!, users))
                launch(Dispatchers.Main) { diffResult.dispatchUpdatesTo(this@UserAdapter) }
                this@UserAdapter.users = users
            } finally {
                //println("finally DiffUtil block is running")
            }
        }
    }

    fun cancelAllRequests() = job.cancel()

}
