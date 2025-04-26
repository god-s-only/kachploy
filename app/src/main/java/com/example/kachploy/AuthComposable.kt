package com.example.kachploy

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kachploy.feature.auth.profile.ProfileScreen
import com.example.kachploy.feature.auth.signin.SignInScreen
import com.example.kachploy.feature.auth.signup.SignUpScreen

@Composable
fun AuthApp(){
    val navController = rememberNavController()
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        NavHost(navController = navController, startDestination = "signin"){
            composable("signin") {
                SignInScreen(navController)
            }
            composable("signup") {
                SignUpScreen(navController)
            }
            composable("profile") {
                ProfileScreen(navController)
            }
        }
    }
}