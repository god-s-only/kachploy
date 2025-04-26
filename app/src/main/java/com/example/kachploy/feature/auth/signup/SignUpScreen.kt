package com.example.kachploy.feature.auth.signup

import android.graphics.drawable.Icon
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kachploy.R

@Composable
fun SignUpScreen(navController: NavController) {
    val context = LocalContext.current
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var cPassword by remember { mutableStateOf("") }
    val viewModel = hiltViewModel<SignUpViewModel>()
    val uiState = viewModel.state.collectAsState()

    LaunchedEffect(key1 = uiState.value) {
        when (uiState.value) {
            is SignUpState.Success -> {
                navController.navigate("signin")
            }
            is SignUpState.Error -> {
                Toast.makeText(context, "Sign up error", Toast.LENGTH_LONG).show()
            }
            is SignUpState.Pending -> {
                Toast.makeText(context, "Check mail for email", Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxSize().padding(it)
        ) {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back"
                )
            }
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Sign Up", fontWeight = FontWeight.Bold, style = TextStyle(
                    fontSize = 32.sp,
                ))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = stringResource(id = R.string.sign_up_info), color = Color.Gray)
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    OutlinedTextField(modifier = Modifier.fillMaxWidth() ,value = fullName, onValueChange = {fullName = it}, label = {Text(text = "Full Name")})
                    OutlinedTextField(modifier = Modifier.fillMaxWidth() ,value = email, onValueChange = {email = it}, label = {Text(text = "Email")})
                    OutlinedTextField(modifier = Modifier.fillMaxWidth() ,value = password, onValueChange = {password = it}, visualTransformation = PasswordVisualTransformation(),label = {Text(text = "Password")})
                    OutlinedTextField(modifier = Modifier.fillMaxWidth() ,value = cPassword, onValueChange = {cPassword = it}, visualTransformation = PasswordVisualTransformation(),isError = password != cPassword,label = {Text(text = "Confirm Password")})
                    Button(onClick = {
                        viewModel.createUser(fullName, email, password)
                    }, modifier = Modifier.fillMaxWidth().height(45.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors().copy(
                            containerColor = Color.Blue
                        ),
                        enabled = fullName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && cPassword.isNotEmpty() && uiState.value is SignUpState.Nothing) {
                        Text(text = "Register", color = Color.White)
                    }
                    if(uiState.value == SignUpState.Loading){
                        CircularProgressIndicator()
                    }else{
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Already have an account?")

                            TextButton(onClick = {navController.navigate("signin")}) {
                                Text(text = "Login", color = Color.Blue)
                            }
                        }
                    }

                }
               

            }

        }
    }
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview(){
    SignUpScreen(rememberNavController())
}