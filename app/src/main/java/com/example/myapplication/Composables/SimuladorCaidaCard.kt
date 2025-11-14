package com.example.myapplication.Composables

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.Firebase.EscribirFirebase

@Composable
fun SimuladorCaida(
    modifier: Modifier = Modifier,
    repository: EscribirFirebase = remember { EscribirFirebase() }
) {
    var pulsoTexto by remember { mutableStateOf("") }
    var inclinacion by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Simulación manual", style = MaterialTheme.typography.titleMedium)

            PulsoTextField(
                value = pulsoTexto,
                onValueChange = { pulsoTexto = it }
            )

            ActionRow(
                inclinacion = inclinacion,
                onInclinacionChange = { inclinacion = it },
                pulsoTexto = pulsoTexto,
                onEnviar = {
                    val pulso = pulsoTexto.toDoubleOrNull()
                    if (pulso != null) {
                        repository.escribirArduinoSimulado(pulso, inclinacion)
                        repository.guardarRegistroCaida(pulso = pulso, caida = inclinacion)
                    }
                }
            )

        }
    }
}

@Composable
private fun PulsoTextField(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it.filter { c -> c.isDigit() || c == '.' }) },
        label = { Text("Pulso (BPM)") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun ActionRow(
    inclinacion: Boolean,
    onInclinacionChange: (Boolean) -> Unit,
    pulsoTexto: String,
    onEnviar: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        FilterChip(
            selected = inclinacion,
            onClick = { onInclinacionChange(!inclinacion) },
            label = { Text(if (inclinacion) "Inclinación: Si" else "Inclinación: No") },
            modifier = Modifier.weight(1f)
        )

        Button(
            onClick = onEnviar,
            enabled = pulsoTexto.isNotBlank(),
            modifier = Modifier
                .weight(1f)
                .height(40.dp)
        ) {
            Text("Enviar")
        }
    }
}

@Composable
private fun MensajeEstado(mensaje: String) {
    Text(
        mensaje,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.primary
    )
}
