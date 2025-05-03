package com.example.kachploy.feature.auth.profile

import android.Manifest
import android.net.Uri
import android.os.Environment
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.kachploy.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun ProfileScreen(navController: NavController) {
    val showDialog = remember { mutableStateOf(false) }
    var viewModel = hiltViewModel<ProfileViewModel>()
    val address = remember { mutableStateOf("") }
    var profileState = viewModel.profileState.collectAsState()
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val availability = remember { mutableStateOf("") }
    val yearsOfExperience = remember { mutableStateOf("") }
    var phoneNumber = remember { mutableStateOf("") }
    val painter = rememberAsyncImagePainter(
        model = imageUri,
        contentScale = ContentScale.Fit
    )
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if(success){

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
        val timeStamp = SimpleDateFormat("yyyy:MM:dd HH:mm:ss", Locale.getDefault()).format(Date())
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
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Blue,
                            Color.White
                        )
                    )
                )
        ) {
            // Back button
            IconButton(
                onClick = { /* Handle back navigation */ },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    modifier = Modifier.padding(8.dp),
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

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
                                painter = painterResource(id = R.drawable.google),
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

                    // Edit Profile Title
                    Text(
                        text = "Edit Profile",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )


                    // Phone number field
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Phone Number",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )

                        OutlinedTextField(
                            value = phoneNumber.value,
                            onValueChange = { phoneNumber.value = it },
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

                        ProfileTextField("Availability", availability.value)
                        ProfileTextField("Years of experience", yearsOfExperience.value)
                        ProfileTextField("Address", address.value)
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
                        enabled = imageUri.value != null,
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
fun ProfileTextField(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = { /* Handle value change */ },
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