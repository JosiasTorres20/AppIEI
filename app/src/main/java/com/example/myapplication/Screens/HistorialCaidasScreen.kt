package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.Firebase.models.RegistroCaida
import com.example.myapplication.Composables.BottomBar
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialCaidasScreen(
    navController: androidx.navigation.NavHostController,
    modifier: Modifier = Modifier
) {
    var eventos by remember { mutableStateOf<List<RegistroCaida>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val ref = FirebaseDatabase.getInstance()
            .reference
            .child("sensor_data")
            .child("fall_history")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                eventos = snapshot.children.mapNotNull {
                    it.getValue(RegistroCaida::class.java)
                }.sortedByDescending { it.timestamp ?: 0L }
                loading = false
            }
            override fun onCancelled(error: DatabaseError) {
                loading = false
            }
        })
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Historial de caídas") },
                navigationIcon = {
                    Icon(imageVector = Icons.Default.History, contentDescription = "Historial")
                }
            )
        },
        bottomBar = {
            BottomBar(navController)
        }
    ) { padding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                loading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
                eventos.isEmpty() -> Text(
                    "Sin eventos registrados",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.Center)
                )
                else -> {
                    LazyColumn(
                        contentPadding = PaddingValues(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(
                            items = eventos,
                            key = { it.id ?: (it.timestamp ?: 0L).toString() }
                        ) { ev ->
                            ListItem(
                                headlineContent = {
                                    Text(formatearFecha(ev.timestamp))
                                },
                                supportingContent = {
                                    Text("Pulso: ${ev.pulso?.toInt() ?: 0} BPM")
                                },
                                leadingContent = {
                                    Icon(
                                        imageVector = Icons.Default.Warning,
                                        contentDescription = "Caída"
                                    )
                                }
                            )
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }
}

private fun formatearFecha(ts: Long?): String {
    if (ts == null) return "-"
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return sdf.format(Date(ts))
}
