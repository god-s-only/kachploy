package com.example.kachploy.activities.auth.signin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kachploy.MainActivity
import com.example.kachploy.activities.ui.theme.KachPloyTheme
import com.example.kachploy.R
import com.example.kachploy.activities.auth.signup.SignUpActivity

class SignInActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KachPloyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SignInScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun SignInScreen(modifier: Modifier = Modifier){
    var email by remember { mutableStateOf("") }
    var context = LocalContext.current
    var password by remember { mutableStateOf("") }
    var viewModel = hiltViewModel<SignInViewModel>()
    var uiState = viewModel.state.collectAsState()

    LaunchedEffect(key1 = uiState.value) {
        when(uiState.value){
            is SignInState.Success -> {
                context.startActivity(Intent(context, MainActivity::class.java))
            }
            is SignInState.Error -> {
                Toast.makeText(context, "Sign in error", Toast.LENGTH_LONG).show()
            }
            is SignInState.Pending -> {
                Toast.makeText(context, "Check mail to verify email", Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    Column(modifier = modifier.fillMaxSize()) {

        Row(modifier = modifier.padding(top = 25.dp, bottom = 15.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Image(modifier = modifier.size(40.dp) ,painter = painterResource(id = R.drawable.logo), contentDescription = "logo")
            Text(text = "KachPloy", fontWeight = FontWeight.Bold)
        }

        Text(modifier = modifier.padding(15.dp),
            text = "Sign in to your\nAccount", style = TextStyle(
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        ))
        Text(modifier = modifier.padding(start = 15.dp, bottom = 20.dp),
            text = "Enter your email and password to log in", style = TextStyle(
            fontSize = 12.sp
        ))

        OutlinedTextField(modifier = modifier.fillMaxWidth().padding(horizontal = 15.dp).clip(RoundedCornerShape(10.dp)),
            value = email,
            onValueChange = {email = it},
            label = {Text(text = "Email")},
            textStyle = TextStyle(color = Color.Black),
            colors = TextFieldDefaults.colors().copy(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedPlaceholderColor = Color.LightGray,
                unfocusedPlaceholderColor = Color.Black
            )
        )
        OutlinedTextField(modifier = modifier.fillMaxWidth().padding(horizontal = 15.dp, vertical = 8.dp).clip(RoundedCornerShape(10.dp)),
            value = password,
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = {password = it},
            label = {Text(text = "Password")},
            textStyle = TextStyle(color = Color.Black),
            colors = TextFieldDefaults.colors().copy(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedPlaceholderColor = Color.LightGray,
                unfocusedPlaceholderColor = Color.Black
            )
        )
        TextButton(modifier = modifier.align(Alignment.End),onClick = {}) {
            Text(text = "Forgot Password?", color = Color.Blue, fontWeight = FontWeight.SemiBold)
        }
        Spacer(modifier = modifier.height(10.dp))
        Button(modifier = modifier.fillMaxWidth().height(50.dp).padding(horizontal = 15.dp),
            onClick = {viewModel.signInUser(email, password)},
            enabled = email.isNotEmpty() && password.isNotEmpty() && uiState.value == SignInState.Nothing,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue
            )) {
            Text(text = "Login ")
        }
        if(uiState.value == SignInState.Loading){
            CircularProgressIndicator()
        }else{
            Row(modifier = modifier.padding(25.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                HorizontalDivider(modifier = modifier.fillMaxWidth(0.45f))
                Text(text = "Or", color = Color.Gray)
                HorizontalDivider()
            }
            Button(modifier = modifier.fillMaxWidth().height(50.dp).padding(horizontal = 15.dp),
                onClick = {},
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, Color.LightGray),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                )) {

                Text(text = "Continue with Google", color = Color.Black)
            }
            Spacer(modifier = modifier.height(20.dp))
            Button(modifier = modifier.fillMaxWidth().height(50.dp).padding(horizontal = 15.dp),
                onClick = {},
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, Color.LightGray),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                )) {

                Text(text = "Continue with Facebook", color = Color.Black)
            }
            Row(modifier = modifier.fillMaxWidth().padding(20.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Don't have an account?")
                TextButton(onClick = {navigateSignUpScreen(context)}) { Text(text = "Sign Up", color = Color.Blue, fontWeight = FontWeight.SemiBold) }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KachPloyTheme {
        SignInScreen()
    }
}
private fun navigateSignUpScreen(context: Context){
    context.startActivity(Intent(context, SignUpActivity::class.java))
}