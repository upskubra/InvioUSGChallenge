package com.example.inviousgchallenge.data.firebase.auth

import com.example.inviousgchallenge.data.model.User
import com.example.inviousgchallenge.util.FirebaseState
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val authResult: AuthResult
) : AuthDataSource {
    override fun anonymousSignIn(): Flow<FirebaseUser> = flow {
        if (authResult.user != null) {
            emit(authResult.user!!)
        } else {
            auth.signInAnonymously().addOnSuccessListener { authResult ->
                (authResult.user)
            }.addOnFailureListener {
                it.localizedMessage
            }
        }
    }
}







