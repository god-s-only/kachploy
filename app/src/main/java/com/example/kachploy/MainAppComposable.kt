package com.example.kachploy

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.example.kachploy.tabview.TabBarItem
import com.example.kachploy.tabview.TabView
import com.google.firebase.Firebase
import com.example.kachploy.R
import com.example.kachploy.util.Routes
import com.google.firebase.auth.auth

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainApp() {
    val navController = rememberNavController()
    val homeTab = TabBarItem(Routes.jobs, Icons.Filled.Search, Icons.Filled.Search)
    val proposalsTab = TabBarItem(Routes.proposals, Icons.Filled.DateRange, Icons.Filled.DateRange)
    val jobsTab = TabBarItem(Routes.contracts, Icons.Filled.MailOutline, Icons.Filled.MailOutline)
    val itemsTab = TabBarItem(Routes.messages, Icons.Filled.Email, Icons.Filled.Email)
    val alertsTab = TabBarItem(Routes.alerts, Icons.Filled.Notifications, Icons.Filled.Notifications)

    val tabItems = listOf(
        homeTab, proposalsTab, jobsTab, itemsTab, alertsTab
    )
    Scaffold(
        bottomBar = { TabView(tabItems, navController) },
        modifier = Modifier.fillMaxSize()
    ) {
        NavHost(navController = navController, startDestination = homeTab.title){
            composable(homeTab.title) {
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
            composable(proposalsTab.title) {
                Text(text = proposalsTab.title)
            }
            composable(jobsTab.title) {
                Text(text = jobsTab.title)
            }
            composable(itemsTab.title) {
                Text(text = itemsTab.title)
            }
            composable(alertsTab.title) {
                Text(text = alertsTab.title)
            }
        }
    }
}