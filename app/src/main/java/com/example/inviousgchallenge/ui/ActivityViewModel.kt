package com.example.inviousgchallenge.ui

import android.app.Application
import com.example.inviousgchallenge.data.model.FeedViewState
import com.example.inviousgchallenge.data.repository.AuthRepository
import com.example.inviousgchallenge.util.FirebaseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ActivityViewModel(application: Application, private val authRepository: AuthRepository) :
    BaseViewModel(application) {
    private val _authState = MutableStateFlow(FeedViewState())
    val authState: StateFlow<FeedViewState> = _authState.asStateFlow()

    suspend fun signIn() {
        authRepository.anonymousSignIn().collect { state ->
            when (state) {
                is FirebaseState.Success -> {
                    _authState.value =
                        state.data?.let {
                            FeedViewState(
                                user = it,
                                signIn = true,
                                loading = false
                            )
                        }!!
                }
                is FirebaseState.Failure -> {
                    _authState.update {
                        it.copy(
                            loading = false,
                            signIn = false,
                            error = it.error
                        )
                    }
                }
                is FirebaseState.Loading -> {
                    _authState.update {
                        it.copy(loading = true)
                    }
                }
            }
        }
    }
}