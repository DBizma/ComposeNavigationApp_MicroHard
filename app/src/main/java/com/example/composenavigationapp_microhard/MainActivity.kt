package com.example.composenavigationapp_microhard


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.navigation.compose.rememberNavController
import com.example.composenavigationapp_microhard.ui.navigation.RootNavGraph
import com.example.composenavigationapp_microhard.ui.theme.ComposeNavigationApp_MicroHardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeNavigationApp_MicroHardTheme {
                val navController = rememberNavController()
                RootNavGraph(navController = navController)
            }
        }
    }
}