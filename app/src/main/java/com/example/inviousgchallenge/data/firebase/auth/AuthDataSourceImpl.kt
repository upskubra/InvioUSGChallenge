package com.example.inviousgchallenge.data.firebase.auth

import com.example.inviousgchallenge.util.FirebaseState
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val auth: FirebaseAuth) : AuthDataSource {
    override fun anonymousSignIn(): Flow<FirebaseState<FirebaseUser>> = flow {
        val user = auth.currentUser
        if (user != null) {
            emit(FirebaseState.Success(user))
        } else {
            auth.signInAnonymously().result?.let {
                if (it.user != null) {
                    emit(FirebaseState.Success(it.user!!))
                } else {
                    emit(FirebaseState.Failure("Error"))
                }
            }
        }
    }
}







