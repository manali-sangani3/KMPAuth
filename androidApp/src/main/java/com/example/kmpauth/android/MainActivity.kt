package com.example.kmpauth.android

import AppModule
import AppRoot
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val manager = AppModule.authManager

        setContent {
            AppRoot(authManager = manager)
        }
    }
}
