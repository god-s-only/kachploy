package com.example.kachploy.feature.auth.signin

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.widget.Toast
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kachploy.R

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun SignInScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var context = LocalContext.current
    var password by remember { mutableStateOf("") }
    var viewModel = hiltViewModel<SignInViewModel>()
    var uiState = viewModel.state.collectAsState()

    LaunchedEffect(key1 = uiState.value) {
        when(uiState.value) {
            is SignInState.Success -> {
                navController.navigate("homescreen")
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

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize().padding(it)) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BoxWithConstraints(
                    modifier = Modifier.fillMaxWidth().padding(top = 6.dp, bottom = 6.dp)
                ) {
                    Row(
                        modifier = Modifier.align(Alignment.CenterStart).padding(horizontal = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier.size(40.dp),
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "logo"
                        )
                        Text(text = "KachPloy", fontWeight = FontWeight.Bold)
                    }
                }

                BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.align(Alignment.CenterStart).padding(horizontal = 16.dp, vertical = 5.dp)) {
                        Text(
                            text = "Sign in to your\nAccount",
                            style = TextStyle(
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            modifier = Modifier.padding(bottom = 10.dp),
                            text = "Enter your email and password to log in",
                            style = TextStyle(fontSize = 12.sp)
                        )
                    }
                }

                BoxWithConstraints(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)),
                        value = email,
                        onValueChange = { email = it },
                        label = { Text(text = "Email") },
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
                }

                BoxWithConstraints(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)),
                        value = password,
                        visualTransformation = PasswordVisualTransformation(),
                        onValueChange = { password = it },
                        label = { Text(text = "Password") },
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
                }

                BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                    TextButton(
                        modifier = Modifier.align(Alignment.CenterEnd).padding(end = 16.dp),
                        onClick = {}
                    ) {
                        Text(
                            text = "Forgot Password?",
                            color = Color.Blue,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                BoxWithConstraints(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                    Button(
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        onClick = { viewModel.signInUser(email, password) },
                        enabled = email.isNotEmpty() && password.isNotEmpty() && uiState.value == SignInState.Nothing,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
                    ) {
                        Text(text = "Login")
                    }
                }

                if (uiState.value == SignInState.Loading) {
                    BoxWithConstraints(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    BoxWithConstraints(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 20.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            HorizontalDivider(modifier = Modifier.weight(1f))
                            Text(text = "Or", color = Color.Gray)
                            HorizontalDivider(modifier = Modifier.weight(1f))
                        }
                    }

                    BoxWithConstraints(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                        Button(
                            modifier = Modifier.fillMaxWidth().height(50.dp),
                            onClick = {},
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, Color.LightGray),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.google),
                                    contentDescription = null,
                                    modifier = Modifier.size(ButtonDefaults.IconSize)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(text = "Continue with Google", color = Color.Black)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    BoxWithConstraints(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                        Button(
                            modifier = Modifier.fillMaxWidth().height(50.dp),
                            onClick = {},
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, Color.LightGray),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.facebook),
                                    contentDescription = null,
                                    modifier = Modifier.size(ButtonDefaults.IconSize)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(text = "Continue with Facebook", color = Color.Black)
                            }
                        }
                    }

                    BoxWithConstraints(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Don't have an account?")
                            TextButton(onClick = { navController.navigate("signup") }) {
                                Text(
                                    text = "Sign Up",
                                    color = Color.Blue,
                                    fontWeight = FontWeight.SemiBold
                                )
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
fun GreetingPreview() {
    SignInScreen(navController = rememberNavController())
}

