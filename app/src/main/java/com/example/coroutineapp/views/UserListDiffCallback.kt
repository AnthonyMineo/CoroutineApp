package com.example.coroutineapp.views

import android.support.v7.util.DiffUtil
import com.example.coroutineapp.models.GithubUser

class UserListDiffCallback(private val oldList: List<GithubUser>, private val newList: List<GithubUser>): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return if (oldList.isEmpty()) {
            0
        } else {
            oldList.size
        }
    }

    override fun getNewListSize(): Int {
        return if (newList.isEmpty()) {
            0
        } else {
            newList.size
        }
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        return old.login == new.login
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }


}