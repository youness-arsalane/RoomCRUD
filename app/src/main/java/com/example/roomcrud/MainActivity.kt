package com.example.roomcrud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.roomcrud.ui.navigation.AppNavHost
import com.example.roomcrud.ui.theme.RoomCRUDTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoomCRUDTheme {
                AppNavHost(
                    navController = rememberNavController()
                )
            }
        }
    }
}