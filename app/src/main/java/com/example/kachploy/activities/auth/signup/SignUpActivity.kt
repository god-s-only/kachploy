package com.example.kachploy.activities.auth.signup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kachploy.MainActivity
import com.example.kachploy.R
import com.example.kachploy.activities.auth.signin.SignInActivity
import com.example.kachploy.activities.auth.signup.ui.theme.KachPloyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KachPloyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SignUpScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun SignUpScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var cPassword by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var cShowPassword by remember { mutableStateOf(false) }
    val viewModel = hiltViewModel<SignUpViewModel>()
    val uiState = viewModel.state.collectAsState()

    LaunchedEffect(key1 = uiState.value) {
        when(uiState.value) {
            is SignUpState.Success -> {
                Toast.makeText(context, "Sign up successful", Toast.LENGTH_LONG).show()
                context.startActivity(Intent(context, SignInActivity::class.java))
            }
            is SignUpState.Error -> {
                Toast.makeText(context, "Sign up error", Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = modifier.padding(top = 25.dp, start = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navigateToSignIn(context) }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }

        Text(
            modifier = modifier.padding(start = 15.dp, top = 20.dp),
            text = "Sign up",
            style = TextStyle(
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Text(
            modifier = modifier.padding(start = 15.dp, bottom = 20.dp),
            text = "Create an account to continue!",
            style = TextStyle(fontSize = 14.sp)
        )

        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(10.dp)),
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text(text = "Full Name") },
            textStyle = TextStyle(color = Color.Black),
            colors = TextFieldDefaults.colors().copy(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )

        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(10.dp)),
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email") },
            textStyle = TextStyle(color = Color.Black),
            colors = TextFieldDefaults.colors().copy(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )

        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(10.dp)),
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") },
            textStyle = TextStyle(color = Color.Black),
            colors = TextFieldDefaults.colors().copy(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            trailingIcon = {
                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(
                        painter = painterResource(
                            id = if (showPassword) R.drawable.ic_launcher_background else R.drawable.ic_launcher_foreground
                        ),
                        contentDescription = if (showPassword) "Hide password" else "Show password"
                    )
                }
            }
        )


        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(10.dp)),
            value = cPassword,
            onValueChange = { cPassword = it },
            label = { Text(text = "Confirm Password") },
            textStyle = TextStyle(color = Color.Black),
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            colors = TextFieldDefaults.colors().copy(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            trailingIcon = {
                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(
                        painter = painterResource(
                            id = if (cShowPassword) R.drawable.ic_launcher_background else R.drawable.ic_launcher_foreground
                        ),
                        contentDescription = if (cShowPassword) "Hide password" else "Show password"
                    )
                }
            }
        )

        Spacer(modifier = modifier.height(20.dp))

        Button(
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 15.dp),
            onClick = {
                viewModel.createUser(fullName, email, password)
            },
            enabled = fullName.isNotEmpty() && email.isNotEmpty() &&
                    cPassword.isNotEmpty() &&
                    password.isNotEmpty() && uiState.value !is SignUpState.Loading,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue
            )
        ) {
            Text(text = "Register")
        }

        if(uiState.value is SignUpState.Loading){
            CircularProgressIndicator()
        }else{
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Already have an account?",
                    style = TextStyle(fontSize = 14.sp)
                )
                TextButton(onClick = { navigateToSignIn(context) }) {
                    Text(
                        text = "Login",
                        color = Color.Blue,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    KachPloyTheme {
        SignUpScreen()
    }
}

private fun navigateToSignIn(context: Context) {
    context.startActivity(Intent(context, SignInActivity::class.java))
}