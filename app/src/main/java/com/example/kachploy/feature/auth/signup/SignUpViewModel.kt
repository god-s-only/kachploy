package com.example.kachploy.feature.auth.signup

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(): ViewModel(){
    private val _state = MutableStateFlow<SignUpState>(SignUpState.Nothing)
    val state = _state.asStateFlow()

    fun createUser(fullName: String, email: String, password: String){
        _state.value = SignUpState.Loading
        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    task.result.user?.let {
                        it.updateProfile(UserProfileChangeRequest.Builder()
                            .setDisplayName(fullName)
                            .build()).addOnCompleteListener {
                                _state.value = SignUpState.Pending
                                Firebase.auth.currentUser?.sendEmailVerification()
                                    ?.addOnCompleteListener {
                                        if(it.isSuccessful){
                                            _state.value = SignUpState.Success
                                        }else{
                                            _state.value = SignUpState.Error
                                        }
                                    }
                            }
                        }
                }else{
                    _state.value = SignUpState.Error
                }
            }
    }
}
sealed class SignUpState{
    object Nothing: SignUpState()
    object Pending: SignUpState()
    object Loading: SignUpState()
    object Success: SignUpState()
    object Error: SignUpState()
}