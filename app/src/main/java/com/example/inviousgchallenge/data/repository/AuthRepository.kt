package com.example.inviousgchallenge.data.repository

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
     fun anonymousSignIn(): Flow<FirebaseUser>
}