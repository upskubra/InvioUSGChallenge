package com.example.inviousgchallenge.data.repository

import com.example.inviousgchallenge.data.firebase.auth.AuthDataSource
import com.example.inviousgchallenge.util.FirebaseState
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : AuthRepository {
    override fun anonymousSignIn(): Flow<FirebaseState<FirebaseUser>> {
        return authDataSource.anonymousSignIn()
    }
}
