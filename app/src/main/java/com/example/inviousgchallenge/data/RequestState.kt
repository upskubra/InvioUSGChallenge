package com.example.inviousgchallenge.data


sealed class RequestState<T>{
    class Loading<T>: RequestState<T>()
    data class Error<T>(val exception: Throwable): RequestState<T>()
    data class Success<T>(val data: T): RequestState<T>()
}