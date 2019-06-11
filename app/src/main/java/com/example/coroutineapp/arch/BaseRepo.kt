package com.example.coroutineapp.arch

import android.util.Log
import java.io.IOException
import com.example.coroutineapp.utils.Result
import retrofit2.Response

open class BaseRepo {

    suspend fun <T : Any> apiCall(call: suspend () -> Response<T>, errorMessage: String): T? {

        val result : Result<T> = apiResult(call,errorMessage)
        var data : T? = null

        when(result) {
            is Result.Success ->
                data = result.data
            is Result.Error -> {
                Log.d("1.DataRepository", "$errorMessage & Exception - ${result.exception}")
            }
        }
        return data
    }

    private suspend fun <T: Any> apiResult(call: suspend ()-> Response<T>, errorMessage: String) : Result<T>{
        val response = call.invoke()
        return if (response.isSuccessful)
            Result.Success(response.body()!!)
        else
            Result.Error(IOException("OOps .. Something went wrong during apiResult due to  $errorMessage"))
    }
}