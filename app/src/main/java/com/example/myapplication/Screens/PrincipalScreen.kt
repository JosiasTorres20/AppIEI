package com.example.myapplication.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.myapplication.Composables.BottomBar

@Composable
fun PrincipalScreen(navController: NavHostController) {
    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) {paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Text("Bienvenido!")
        }
    }

}

