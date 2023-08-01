package com.example.weatherassignment.utils

sealed class RepoStatus<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T, message: String) : RepoStatus<T>(data, message)
    class Error<T>(message: String, data: T? = null) : RepoStatus<T>(data, message)
}
