package com.example.kachploy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.kachploy.ui.theme.KachPloyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KachPloyTheme {
                AuthApp()
            }
        }
    }
}



