package com.example.kachploy.feature.auth.signin

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor() : ViewModel(){
    private val _state = MutableStateFlow<SignInState>(SignInState.Nothing)
    val state = _state.asStateFlow()
    fun signInUser(email: String, password: String){
        _state.value = SignInState.Loading
        Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Firebase.auth.currentUser?.sendEmailVerification()
                        ?.addOnCompleteListener {
                            if(it.isSuccessful){
                                task.result.user?.let {
                                    _state.value = SignInState.Success
                            }
                        }
                    }
                    _state.value = SignInState.Error
                }else{
                    _state.value = SignInState.Error
                }
            }
    }
}
sealed class SignInState{
    object Nothing: SignInState()
    object Loading: SignInState()
    object Success: SignInState()
    object Error: SignInState()
}