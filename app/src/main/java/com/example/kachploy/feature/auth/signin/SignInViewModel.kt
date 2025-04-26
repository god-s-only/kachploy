package com.example.kachploy.feature.auth.signin

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor() : ViewModel(){
    private val _state = MutableStateFlow<SignInState>(SignInState.Nothing)
    val state = _state.asStateFlow()

    fun signInUser(email: String, password: String) {
        _state.value = SignInState.Loading

        Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = Firebase.auth.currentUser
                    if (user?.isEmailVerified == true) {
                        _state.value = SignInState.Success
                    } else {
                        user?.sendEmailVerification()
                            ?.addOnCompleteListener { emailTask ->
                                _state.value = if (emailTask.isSuccessful) {
                                    SignInState.Pending
                                } else {
                                    SignInState.Error
                                }
                            }
                    }
                } else {
                    _state.value = SignInState.Error
                }
            }
    }
}
sealed class SignInState{
    object Nothing: SignInState()
    object Loading: SignInState()
    object Pending: SignInState()
    object Success: SignInState()
    object Error: SignInState()
}