package com.example.kachploy

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kachploy.feature.home.HomeScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun MainApp() {
    val navController = rememberNavController()
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        NavHost(navController = navController, startDestination = "homescreen"){
            composable("homescreen") {
                HomeScreen(navController)
            }
        }
    }
}