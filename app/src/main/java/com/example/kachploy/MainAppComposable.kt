package com.example.kachploy

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.kachploy.feature.home.HomeScreen
import com.example.kachploy.feature.job_details.JobDetailsScreen
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
            composable("jobDetail/{jobDetailId}", arguments = listOf(
                navArgument("jobDetailId"){
                    type = NavType.StringType
                }
            )){
                val jobId = it.arguments?.getString("jobDetailId") ?: ""
                JobDetailsScreen(navController, jobId)
            }
        }
    }
}