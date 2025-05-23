package com.example.kachploy.feature.job_details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kachploy.util.UiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobDetailsScreen(
    navController: NavController,
    jobId: String,
    viewModel: JobDetailsViewModel = hiltViewModel()
){
    LaunchedEffect(key1 = true) {
        viewModel.jobState.collect { event ->
            when(event){
                is UiEvent.ShowSnackBar -> {

                }
                else -> Unit
            }
        }
        viewModel.getJobDetail(jobId)
    }

    val scrollbehaviour = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val scrollState = rememberScrollState()

    val jobDetail

    Scaffold(modifier = Modifier.fillMaxSize(),
        containerColor = Color.White,
        topBar = {
            CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.LightGray,
                titleContentColor = Color.Black
            ),
            title = {
                Text(text = "Job Details", fontWeight = FontWeight.SemiBold)
            },
            navigationIcon = {
                IconButton(onClick = {navController.popBackStack()}) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.Black)
                }
            },
            scrollBehavior = scrollbehaviour
        )
        }
    )
    {
        Column(modifier = Modifier.fillMaxSize()
            .padding(it)) {
            Column(modifier = Modifier
                .padding(10.dp)
                .verticalScroll(scrollState)) {
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "AI App Development Project", fontWeight = FontWeight.SemiBold, color = Color.Black)
                    Card(
                        modifier = Modifier.padding(5.dp),
                        colors = CardDefaults.cardColors().copy(
                            containerColor = Color.Transparent
                        ),
                        shape = CircleShape,
                        border = BorderStroke(1.dp, Color.Black)
                    ){
                        IconButton(onClick = {}) {
                            Icon(imageVector = Icons.Outlined.FavoriteBorder, contentDescription = null)
                        }
                    }
                }
                Text(text = "Posted 29 minutes ago", color = Color.Gray)
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Filled.LocationOn, contentDescription = null)
                    Text(text = "Worldwide", color = Color.Gray)
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = "8 required Connects (0 available)", color = Color.Gray)
                Spacer(modifier = Modifier.height(20.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(30.dp))
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Summary", fontWeight = FontWeight.SemiBold)
                    Text(text = "Summary")
                }
                Spacer(modifier = Modifier.height(20.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(20.dp))
                Row{
                    Icon(imageVector = Icons.Outlined.Info, contentDescription = null, modifier = Modifier.padding(end = 5.dp))
                    Column {
                        Text(text = "$30.00", fontWeight = FontWeight.SemiBold)
                        Text(text = "Fixed Price", color = Color.Gray)
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row{
                    Icon(imageVector = Icons.Outlined.Star, contentDescription = null, modifier = Modifier.padding(end = 5.dp))
                    Column {
                        Text(text = "Entry Level", fontWeight = FontWeight.SemiBold)
                        Text(text = "Experience Level", color = Color.Gray)
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(20.dp))
                Row {
                    Text(text = "Project Type: ", fontWeight = FontWeight.SemiBold)
                    Text(text = "One-time project")
                }
                Spacer(modifier = Modifier.height(20.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(20.dp))
                Column {
                    Text(text = "Skills and Expertise", fontWeight = FontWeight.Bold, style = TextStyle(fontSize = 20.sp))
                }
            }

        }

    }
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview(){
    JobDetailsScreen(rememberNavController(), "")
}