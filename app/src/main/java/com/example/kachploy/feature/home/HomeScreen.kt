package com.example.kachploy.feature.home

import android.view.RoundedCorner
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
import com.example.kachploy.R
import com.example.kachploy.models.JobsModel
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.firebase.FirebaseApp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController){
    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    var searchValue by remember { mutableStateOf("") }
    var viewModel: HomeViewModel = hiltViewModel()
    var loading = remember {
        mutableStateOf(false)
    }
    var homeState = viewModel.homeState.collectAsState()
    val categories = listOf<String>("Best Matches", "Most Recents", "Saved Jobs")

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            MediumTopAppBar(colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White,
                titleContentColor = Color.Black
            ),
                title = {
                    Text(text = "Jobs", maxLines = 1, overflow = TextOverflow.Ellipsis, fontWeight = FontWeight.Bold)
                        },
                navigationIcon = {
                    IconButton(onClick = {}) {
                    Image(painter = painterResource(id = R.drawable.google), contentDescription = "Profile")
                } },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                },
                scrollBehavior = scrollBehaviour
            )
        }) {

        LaunchedEffect(key1 = homeState.value) {
            when(homeState.value){
                is PostHomeState.Loading -> {
                    loading.value = true
                }
                is PostHomeState.Error -> {

                }

                else -> {}
            }
        }

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
fun JobItems(){
    Box(modifier = Modifier.fillMaxWidth()
        .padding(10.dp)
        .background(Color.White)){
        Column(modifier = Modifier.padding(5.dp)) {
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Title", color = Color.Black, maxLines = 2, overflow = TextOverflow.Ellipsis,style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),modifier = Modifier.fillMaxWidth(0.5f).clip(RoundedCornerShape(16.dp)).placeholder(
                    visible = true,
                    highlight = PlaceholderHighlight.shimmer(),
                    color = Color.LightGray
                ))
                Row {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Filled.Favorite, contentDescription = null)
                    }
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Filled.Clear, contentDescription = null)
                    }
                }

            }
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 6.dp).clip(
                RoundedCornerShape(16.dp)).placeholder(
                visible = true,
                highlight = PlaceholderHighlight.shimmer(),
                color = Color.LightGray
            )) {
                Icon(painterResource(id = R.drawable.baseline_shopping_bag_24), contentDescription = null, modifier = Modifier.padding(end = 5.dp))
                Text(text = "Fixed: $2K .Intermediate", color = Color.Gray)
            }
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 6.dp).clip(
                RoundedCornerShape(16.dp)).placeholder(
                visible = true,
                highlight = PlaceholderHighlight.shimmer(),
                color = Color.LightGray
            )) {
                Icon(painterResource(id = R.drawable.proposals), contentDescription = null, modifier = Modifier.size(30.dp).padding(end = 5.dp))
                Text(text = "Fixed: $2K .Intermediate", color = Color.Gray)
            }
            Text(text = "Description", style = MaterialTheme.typography.bodyMedium, maxLines = 2, overflow = TextOverflow.Ellipsis, modifier = Modifier.clip(
                RoundedCornerShape(16.dp)).placeholder(
                visible = true,
                highlight = PlaceholderHighlight.shimmer(),
                color = Color.LightGray
            ))
            Row(modifier = Modifier.fillMaxWidth()) {
                for (x in 1..5){
                    Box(modifier = Modifier
                        .padding(5.dp)
                        .clip(
                            RoundedCornerShape(16.dp)).placeholder(
                            visible = true,
                            highlight = PlaceholderHighlight.shimmer(),
                            color = Color.LightGray
                        )
                        .background(Color.LightGray)
                        .padding(5.dp)
                    ){
                        Text(text = "hahah", color = Color.Gray)
                    }
                }
            }
            Text(text = "2025-05-12", color = Color.Gray, modifier = Modifier.padding(top = 5.dp).clip(
                RoundedCornerShape(16.dp)).placeholder(
                visible = true,
                highlight = PlaceholderHighlight.shimmer(),
                color = Color.LightGray
            ))
        }
    }
}

@Preview
@Composable
fun DefaultPreview(){
    JobItems()
}