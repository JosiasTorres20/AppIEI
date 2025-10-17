package com.example.myapplication.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController


@Composable
fun LoginScreen(navController: NavHostController) {
    var usuario by remember { mutableStateOf("") }
    var pass by remember {mutableStateOf("")}

    Scaffold { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)){
            TextField(
                value = usuario,
                onValueChange = {usuario = it},
                label = {Text("Usuario")}
            )
            TextField(
                value = pass,
                onValueChange = {pass = it},
                label = {Text("Contraseña")}
            )
            Button(
                onClick = {
                    println("Le diste al boton")
                    navController.navigate("principal")
                }
            ){
                Text("Entrar")
            }
        }
    }
}









