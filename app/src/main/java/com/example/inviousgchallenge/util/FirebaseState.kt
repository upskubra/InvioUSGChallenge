package com.example.inviousgchallenge.util

sealed class FirebaseState<out T> {
    object Loading : FirebaseState<Nothing>()

    data class Success<out T>(
        val data: T?
    ) : FirebaseState<T>()

    data class Failure(
        val e: String?
    ) : FirebaseState<Nothing>()
}