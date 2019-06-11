package com.example.coroutineapp.utils

/**
 * Class to handle Network response. It either can be Success with the required data or Error with an exception
 */
sealed class Result<out T: Any> {
    class Success<out T: Any>(val data: T): Result<T>()
    class Error(val exception: Exception): Result<Nothing>()
}