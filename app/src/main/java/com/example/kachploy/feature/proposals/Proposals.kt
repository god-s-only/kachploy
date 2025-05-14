package com.example.kachploy.feature.proposals

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Settings
import com.example.kachploy.R
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProposalsScreen(navController: NavController, content: @Composable () -> Unit){
    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {

                    Row(modifier = Modifier.fillMaxWidth()) {
                        IconButton(onClick = {}) {
                            AsyncImage(model = Firebase.auth.currentUser?.photoUrl, contentDescription = null)
                        }
                        Text(Firebase.auth.currentUser?.displayName.toString(), modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleLarge)
                    }
                    Spacer(Modifier.height(12.dp))

                    HorizontalDivider()
                    Row {
                        Icon(imageVector = Icons.Filled.Person, contentDescription = null)
                        NavigationDrawerItem(
                            label = { Text("Profile") },
                            selected = false,
                            onClick = { /* Handle click */ }
                        )
                    }
                    Row {
                        Icon(painter = painterResource(id = R.drawable.proposals), contentDescription = null)
                        NavigationDrawerItem(
                            label = { Text("My Stats") },
                            selected = false,
                            onClick = { /* Handle click */ }
                        )
                    }


                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    Text("Section 2", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleMedium)
                    NavigationDrawerItem(
                        label = { Text("Settings") },
                        selected = false,
                        icon = { Icon(Icons.Outlined.Settings, contentDescription = null) },
                        badge = { Text("20") }, // Placeholder
                        onClick = { /* Handle click */ }
                    )
                    NavigationDrawerItem(
                        label = { Text("Help and feedback") },
                        selected = false,
                        icon = { Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null) },
                        onClick = { /* Handle click */ },
                    )
                    Spacer(Modifier.height(12.dp))
                }
            }
        },
        drawerState = drawerState
    ){
        Scaffold(modifier = Modifier.fillMaxSize(),
            topBar = {
                MediumTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.White,
                        scrolledContainerColor = Color.LightGray,
                        titleContentColor = Color.Black
                    ),
                    title = { Text(text = "My Proposals", fontWeight = FontWeight.Bold, overflow = TextOverflow.Ellipsis) },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if(drawerState.isClosed){
                                    drawerState.open()
                                }else{
                                    drawerState.close()
                                }
                            }
                        }) {
                            AsyncImage(model = Firebase.auth.currentUser?.photoUrl, contentDescription = null, contentScale = ContentScale.Crop)
                        }
                    },
                    scrollBehavior = scrollBehaviour
                )
            }) {
            Column(modifier = Modifier.padding(it)
                .fillMaxSize()) {

            }
        }

    }

}