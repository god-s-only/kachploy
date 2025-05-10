package com.example.kachploy.feature.auth.profile

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.kachploy.MainActivity
import com.example.kachploy.R
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun ProfileScreen(navController: NavController) {
    val showDialog = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var viewModel = hiltViewModel<ProfileViewModel>()
    val address = remember { mutableStateOf("") }
    val context = LocalContext.current
    var profileState = viewModel.profileState.collectAsState()
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val availability = remember { mutableStateOf("") }
    val yearsOfExperience = remember { mutableStateOf("") }
    var phoneNumber = remember { mutableStateOf("") }
    val painter = when(imageUri){
        null -> {
            painterResource(id = R.drawable.baseline_person_24)
        }
        else -> {
            rememberAsyncImagePainter(model = imageUri)
        }
    }
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if(success){
            Toast.makeText(context, "Photo taken successfully", Toast.LENGTH_LONG).show()
        }else{

        }

    }
    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        uri: Uri? ->
        uri?.let {
            imageUri.value = it
        }
    }

    fun createImageUri(): Uri{
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = ContextCompat.getExternalFilesDirs(
            navController.context,
            Environment.DIRECTORY_PICTURES
        ).first()
        return FileProvider.getUriForFile(navController.context, "${navController.context.packageName}.provider",
            File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir).apply {
                imageUri.value = Uri.fromFile(this)
            })
    }

    val permission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { success ->
        if(success){
            cameraLauncher.launch(createImageUri())
        }

    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {

        LaunchedEffect(key1 = profileState.value) {
            when(profileState.value){
                is ProfileState.Success -> {
                    scope.launch {
                        snackbarHostState.showSnackbar("Profile Updated Successfully")
                        navigateToMainActivity(context)
                    }
                }
                is ProfileState.Error ->{
                    scope.launch {
                        snackbarHostState.showSnackbar("Error updating Profile")
                    }

                }
                else -> {}
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Green,
                            Color.White
                        )
                    )
                )
        ) {
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .align(Alignment.Center),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(modifier = Modifier.padding(bottom = 16.dp)) {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(Color.LightGray)
                        ) {
                            Image(
                                painter = painter ?: painterResource(id = R.drawable.baseline_person_24),
                                contentDescription = "Profile Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                                    .clickable{showDialog.value = true}
                            )
                        }

                        FloatingActionButton(
                            onClick = { showDialog.value = true },
                            modifier = Modifier
                                .size(28.dp)
                                .align(Alignment.BottomEnd),
                            containerColor = Color(0xFF2962FF),
                            contentColor = Color.White
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit Profile Picture",
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    Text(
                        text = "Edit Profile",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )


                    Column(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = phoneNumber.value,
                            onValueChange = { phoneNumber.value = it },
                            label = { Text(text = "Phone Number")},
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            shape = RoundedCornerShape(8.dp),
                            leadingIcon = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "+234",
                                        fontSize = 16.sp
                                    )
                                    Icon(
                                        imageVector = Icons.Default.KeyboardArrowDown,
                                        contentDescription = "Country code",
                                        tint = Color.Black
                                    )
                                    HorizontalDivider(
                                        modifier = Modifier
                                            .height(24.dp)
                                            .width(1.dp)
                                            .padding(horizontal = 8.dp),
                                        color = Color.LightGray
                                    )
                                }
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.LightGray,
                                unfocusedBorderColor = Color.LightGray
                            ),
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = availability.value,
                            label = {Text(text = "Availability")},
                            onValueChange = {availability.value = it},
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.LightGray,
                                unfocusedBorderColor = Color.LightGray
                            ),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = yearsOfExperience.value,
                            label = {Text(text = "Years of experience")},
                            onValueChange = {yearsOfExperience.value = it},
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.LightGray,
                                unfocusedBorderColor = Color.LightGray
                            ),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = address.value,
                            label = {Text(text = "Address")},
                            onValueChange = {address.value = it},
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.LightGray,
                                unfocusedBorderColor = Color.LightGray
                            ),
                            singleLine = true
                        )
                    }

                    Button(
                        onClick = {viewModel.saveUserInformation(
                            address.value,
                            availability.value,
                            phoneNumber.value,
                            "Employee",
                            yearsOfExperience.value,
                            imageUri.value
                        )},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .height(48.dp),
                        enabled = imageUri.value != null && profileState.value == ProfileState.Nothing || profileState.value == ProfileState.Error,
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Lock Icon",
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text("Save Information", fontSize = 16.sp)
                    }


                }
                if(profileState.value == ProfileState.Loading){
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
            }
            }
        }
    }
    if(showDialog.value){
        ContentDialogSelection({
            showDialog.value = false
            if(navController.context.checkSelfPermission(Manifest.permission.CAMERA) == android.content.pm.PackageManager.PERMISSION_GRANTED){
                cameraLauncher.launch(createImageUri())
            }else{
                showDialog.value = false
                permission.launch(Manifest.permission.CAMERA)
            }
        }) {
            showDialog.value = false
            imageLauncher.launch("image/*")
        }
    }
    }


@Composable
fun ContentDialogSelection(onCameraSelected : () -> Unit, onImageSelected : () -> Unit){
    AlertDialog(onDismissRequest = {

    },
        confirmButton = {
            TextButton(onClick = onCameraSelected) {
                Text(text = "Camera")
            }
        },
        dismissButton = {
            TextButton(onClick = onImageSelected) {
                Text(text = "Gallery")
            }
        },
        title = {
            Text(text = "Select Source")
        },
        text = {
            Text(text = "Please select a source of your choice for your profile photo")
        })
}

@Preview(showBackground = true)
@Composable
fun Default(){
    ProfileScreen(navController = rememberNavController())
}

private fun navigateToMainActivity(context: Context){
    context.startActivity(Intent(context, MainActivity::class.java))
}