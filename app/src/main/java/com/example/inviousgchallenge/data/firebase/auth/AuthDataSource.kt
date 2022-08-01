package com.example.inviousgchallenge.data.firebase.auth

import com.example.inviousgchallenge.util.FirebaseState
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthDataSource {
   fun anonymousSignIn(): Flow<FirebaseState<FirebaseUser>>
}

