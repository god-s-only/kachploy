package com.example.kachploy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import com.example.kachploy.ui.theme.KachPloyTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.model.Document
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : ComponentActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var userDocument: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KachPloyTheme {
                AuthApp()
            }
            mAuth = FirebaseAuth.getInstance()
            userDocument = FirebaseFirestore.getInstance()
            if(mAuth.currentUser != null){
                if(userDocument.collection("users").document(mAuth.uid!!) != null){
                    navigateMain(LocalContext.current)
                }
            }
        }
    }
}
private fun navigateMain(context: Context){
    context.startActivity(Intent(context, MainActivity::class.java))
}


