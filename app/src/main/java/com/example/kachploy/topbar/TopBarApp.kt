package com.example.kachploy.topbar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Settings
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.kachploy.R
import com.example.kachploy.feature.home.ColumnCategory
import com.example.kachploy.feature.home.JobItems
import com.example.kachploy.feature.home.PostHomeState
import com.example.kachploy.models.JobsModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(title: String, content: @Composable () -> Unit){
    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    content()
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {

                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = {}) {
                            AsyncImage(model = Firebase.auth.currentUser?.photoUrl, contentDescription = null)
                        }
                        Text(Firebase.auth.currentUser?.displayName.toString(), modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleLarge)
                    }
                    Spacer(Modifier.height(12.dp))

                    HorizontalDivider()
                    NavigationDrawerItem(
                        label = { Text("Profile") },
                        selected = false,
                        icon = {Icon(imageVector = Icons.Filled.Person, contentDescription = null)},
                        badge = {Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)},
                        onClick = { /* Handle click */ }
                    )

                    HorizontalDivider()
                    NavigationDrawerItem(
                        label = { Text("My Stats") },
                        selected = false,
                        icon = {Icon(painter = painterResource(id = R.drawable.proposals), contentDescription = null, modifier = Modifier.size(30.dp))},
                        badge = {Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)},
                        onClick = { /* Handle click */ }
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    NavigationDrawerItem(
                        label = { Text("Settings") },
                        selected = false,
                        icon = { Icon(Icons.Outlined.Settings, contentDescription = null) },
                        badge = { Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null) },
                        onClick = { /* Handle click */ }
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

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
                    title = { Text(text = title, fontWeight = FontWeight.Bold, overflow = TextOverflow.Ellipsis) },
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

