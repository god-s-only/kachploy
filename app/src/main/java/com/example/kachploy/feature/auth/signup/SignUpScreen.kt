package com.example.kachploy.feature.auth.signup

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun SignUpScreen(navController: NavController) {
    val context = LocalContext.current
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var cPassword by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var cShowPassword by remember { mutableStateOf(false) }
    val viewModel = hiltViewModel<SignUpViewModel>()
    val uiState = viewModel.state.collectAsState()

    LaunchedEffect(key1 = uiState.value) {
        when (uiState.value) {
            is SignUpState.Success -> {
                Toast.makeText(context, "Sign up successful", Toast.LENGTH_LONG).show()
                navController.navigate("signin")
            }

            is SignUpState.Error -> {
                Toast.makeText(context, "Sign up error", Toast.LENGTH_LONG).show()
            }

            else -> {}
        }
    }

}