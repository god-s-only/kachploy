package com.example.kachploy

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kachploy.feature.auth.signin.SignInScreen
import com.example.kachploy.feature.auth.signup.SignUpScreen
import com.example.kachploy.feature.home.HomeScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun MainApp() {
    val navController = rememberNavController()
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        val start = if(Firebase.auth.currentUser?.uid != null) "homescreen" else "login"
        NavHost(navController = navController, startDestination = start){
            composable("login") {
                SignInScreen(navController)
            }
            composable("signup") {
                SignUpScreen(navController)
            }
            composable("homescreen") {
                HomeScreen(navController)
            }
        }
    }
}