package com.example.kachploy.tabview

import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hierarchy


data class TabBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null
)

@Composable
fun TabView(
    tabItems: List<TabBarItem>,
    navController: NavController
){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        containerColor = Color.White) {
        tabItems.forEach { tabBarItem ->
            val itemIsSelected = currentDestination?.hierarchy?.any {
                it.route == tabBarItem.title
            } == true

            NavigationBarItem(
                selected = itemIsSelected,
                onClick = {
                    navController.navigate(tabBarItem.title){
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    TabBarIconView(
                        isSelected = itemIsSelected,
                        item = tabBarItem
                    )
                },
                label = {
                    Text(text = tabBarItem.title, color = Color.Black)
                }
            )
        }
    }
}

@Composable
fun TabBarIconView(
    isSelected: Boolean,
    item: TabBarItem
){
    BadgedBox(
        badge = {TabBarBadgeView(item.badgeCount)}
    ) {
        Icon(
            imageVector = if(isSelected) item.selectedIcon else item.unselectedIcon,
            contentDescription = item.title
        )
    }
}

@Composable
fun TabBarBadgeView(count: Int? = null){
    if(count != null){
        Badge{
            Text(text = count.toString())
        }
    }
}