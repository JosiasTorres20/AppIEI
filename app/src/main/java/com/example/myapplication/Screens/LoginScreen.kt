package com.example.myapplication.Screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth


@Composable
fun LoginScreen(navController: NavHostController) {
    val auth = FirebaseAuth.getInstance()

    LaunchedEffect(Unit) {
        if (auth.currentUser != null) {
            navController.navigate("principal") {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    var usuario by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var cargando by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF3F4F6)),
            contentAlignment = Alignment.Center
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ){
                Text(
                    text = "Iniciar Sesion",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                TextField(
                    value = usuario,
                    onValueChange = { usuario = it},
                    label = { Text("Usuario")},
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = pass,
                    onValueChange = { pass = it},
                    label = { Text("Contraseña")},
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Button(
                        onClick = {
                            if (usuario.isNotEmpty() && pass.isNotEmpty()){
                                cargando = true
                                auth.signInWithEmailAndPassword(usuario, pass)
                                    .addOnCompleteListener { task ->
                                        cargando = false
                                        if (task.isSuccessful) {
                                            Toast.makeText(
                                                context, "Login exitoso",
                                                Toast.LENGTH_SHORT).show()
                                            navController.navigate(
                                                "principal") {
                                                popUpTo("login")
                                                { inclusive = true }
                                            }
                                        }else {
                                            Toast.makeText(
                                                context, "Error: ${task.exception?.message}",
                                                Toast.LENGTH_SHORT).show()
                                        }
                                    }
                            }
                        },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF53900)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    enabled = !cargando
                ) {
                    if (cargando) {
                        CircularProgressIndicator(
                            modifier = Modifier.height(24.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Entrar", fontSize = 18.sp, color = Color.White)
                    }
                }
            }
        }
    }
}






