package com.example.kachploy.feature.home

import android.view.RoundedCorner
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import com.google.accompanist.placeholder.material3.shimmer
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.kachploy.R
import com.example.kachploy.models.JobsModel
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.auth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController){
    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    var searchValue by remember { mutableStateOf("") }
    var viewModel: HomeViewModel = hiltViewModel()
    val context = LocalContext.current
    var homeState = viewModel.homeState.collectAsState()
    val categories = listOf<String>("Best Matches", "Most Recents", "Saved Jobs")

    Scaffold(modifier = Modifier.fillMaxSize(),
        containerColor = Color.White,
        topBar = {
            MediumTopAppBar(colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White,
                scrolledContainerColor = Color.LightGray,
                titleContentColor = Color.Black
            ),
                title = {
                    Text(text = "Jobs", maxLines = 1, overflow = TextOverflow.Ellipsis, fontWeight = FontWeight.Bold)
                        },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        AsyncImage(model = Firebase.auth.currentUser?.photoUrl, contentDescription = "Profile Picture")
                } },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            tint = Color.Black,
                            contentDescription = "Localized description"
                        )
                    }
                },
                scrollBehavior = scrollBehaviour
            )
        }) {

            when(homeState.value){
                is PostHomeState.Loading -> {
                    val jobs = (homeState.value as PostHomeState.Loading).jobs
                    Column(modifier = Modifier.fillMaxSize()
                        .padding(it)) {
                        LazyColumn(modifier = Modifier.nestedScroll(scrollBehaviour.nestedScrollConnection)) {
                            item {
                                OutlinedTextField(value = searchValue, onValueChange = {searchValue = it}, placeholder = {Text(text = "Search for jobs")}, modifier = Modifier.fillMaxWidth()
                                    .padding(15.dp)
                                    .clip(RoundedCornerShape(5.dp)),
                                    leadingIcon = {
                                        Icon(imageVector = Icons.Filled.Search, contentDescription = null)
                                    })
                            }
                            item {
                                ColumnCategory(categories)
                                HorizontalDivider()
                            }
                            items(jobs) { jobsModel ->
                                JobItems(jobsModel, true)
                            }
                        }
                    }
                }
                is PostHomeState.Success ->{
                    val jobs = (homeState.value as PostHomeState.Success).jobs
                    Column(modifier = Modifier.fillMaxSize()
                        .padding(it)) {
                        LazyColumn(modifier = Modifier.nestedScroll(scrollBehaviour.nestedScrollConnection)) {
                            item {
                                OutlinedTextField(value = searchValue, onValueChange = {searchValue = it}, placeholder = {Text(text = "Search for jobs")}, modifier = Modifier.fillMaxWidth()
                                    .padding(15.dp)
                                    .clip(RoundedCornerShape(5.dp)),
                                    leadingIcon = {
                                        Icon(imageVector = Icons.Filled.Search, contentDescription = null)
                                    })
                            }
                            item {
                                ColumnCategory(categories)
                                HorizontalDivider()
                            }
                            items(jobs) { jobsModel ->
                                JobItems(jobsModel, false)
                            }
                        }
                    }
                }

                else -> {
                    Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                }
            }




    }
}

@Composable
fun ColumnCategory(categories: List<String>){
    var selectedChipIndex by remember {
        mutableStateOf(0)
    }
    LazyRow {
        items(categories.size) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(start = 15.dp, top = 15.dp, bottom = 15.dp)
                    .clickable {
                        selectedChipIndex = it
                    }
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        if (selectedChipIndex == it) Color.Black else Color.LightGray
                    )
                    .padding(15.dp)
            ) {
                Text(text = categories[it], color = if(selectedChipIndex == it) Color.White else Color.Black)
            }
        }
    }
}

@Composable
fun JobItems(jobsModel: JobsModel, loading: Boolean){
    Box(modifier = Modifier.fillMaxWidth()
        .background(Color.White)){
        Column(modifier = Modifier.padding(5.dp)) {
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = jobsModel.title, color = Color.Black, maxLines = 2, overflow = TextOverflow.Ellipsis,style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                ),modifier = Modifier.fillMaxWidth(0.5f).padding(5.dp).clip(RoundedCornerShape(8.dp)).placeholder(
                    visible = loading,
                    highlight = PlaceholderHighlight.shimmer(),
                    color = Color.LightGray
                ))
                Row {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Filled.Favorite, contentDescription = null, tint = Color.Black)
                    }
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Filled.Clear, contentDescription = null, tint = Color.Black)
                    }
                }

            }
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 6.dp).clip(
                RoundedCornerShape(8.dp)).placeholder(
                visible = loading,
                highlight = PlaceholderHighlight.shimmer(),
                color = Color.LightGray
            )) {
                Icon(painterResource(id = R.drawable.baseline_shopping_bag_24), contentDescription = null, modifier = Modifier.padding(end = 5.dp), tint = Color.Black)
                Text(text = "Fixed: $${jobsModel.price} .Intermediate", color = Color.Gray)
            }
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 6.dp).clip(
                RoundedCornerShape(8.dp)).placeholder(
                visible = loading,
                highlight = PlaceholderHighlight.shimmer(),
                color = Color.LightGray
            )) {
                Icon(painterResource(id = R.drawable.proposals), contentDescription = null, modifier = Modifier.size(30.dp).padding(end = 5.dp), tint = Color.Black)
                Text(text = "Proposals: ${jobsModel.proposal}", color = Color.Gray)
            }
            Text(text = jobsModel.description, style = MaterialTheme.typography.bodyMedium, maxLines = 2, overflow = TextOverflow.Ellipsis, modifier = Modifier.padding(5.dp).clip(
                RoundedCornerShape(8.dp)).placeholder(
                visible = loading,
                highlight = PlaceholderHighlight.shimmer(),
                color = Color.LightGray
            ))
            Row(modifier = Modifier.fillMaxWidth()) {
                jobsModel.skillsRequired.forEach { skill ->
                    Box(
                        modifier = Modifier
                            .padding(5.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .placeholder(
                                visible = loading,
                                highlight = PlaceholderHighlight.shimmer(),
                                color = Color.LightGray
                            )
                            .background(Color.LightGray)
                            .padding(5.dp)
                    ) {
                        Text(text = skill, color = Color.Gray)
                    }
                }
            }
                Text(text = jobsModel.createdAt, color = Color.Gray, modifier = Modifier.padding(top = 5.dp, bottom = 8.dp).clip(
                RoundedCornerShape(16.dp)).placeholder(
                visible = loading,
                highlight = PlaceholderHighlight.shimmer(),
                color = Color.LightGray
            ))
            HorizontalDivider()
        }
    }
}

@Preview
@Composable
fun DefaultPreview(){
    HomeScreen(rememberNavController())
}