package com.example.inviousgchallenge.data.firebase.auth

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthDataSource {
   fun anonymousSignIn(): Flow<FirebaseUser>
}

