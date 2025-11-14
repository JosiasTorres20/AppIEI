package com.example.myapplication.Composables


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.Firebase.models.Arduino
import com.example.myapplication.Firebase.MonitorRepository
import com.example.myapplication.Firebase.*

@Composable
fun MonitorCard(
    modifier: Modifier = Modifier,
    repository: MonitorRepository = remember { MonitorRepository() }
) {
    val arduino by repository.arduinoData.collectAsState()

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DispositivoInfo()
            PulsoCircular(arduino?.pulso)
            EstadoCaidaCard(arduino = arduino)
        }
    }

    DisposableEffect(Unit) {
        repository.iniciarEscucha()
        onDispose { repository.detenerEscucha() }
    }
}

@Composable
private fun DispositivoInfo() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "Monitor de Pulsaciones",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
private fun PulsoCircular(pulso: Double?) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Canvas(Modifier.size(180.dp)) {
            drawArc(
                Color.LightGray.copy(alpha = 0.3f),
                -90f,
                360f,
                false,
                style = Stroke(16.dp.toPx(), cap = StrokeCap.Round)
            )

            val progress = ((pulso ?: 0.0).toFloat() / 150f).coerceIn(0f, 1f)
            drawArc(
                Color(0xFFE91E63),
                -90f,
                360f * progress,
                false,
                style = Stroke(16.dp.toPx(), cap = StrokeCap.Round)
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                pulso?.toInt()?.toString() ?: "--",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                "BPM",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun EstadoCaidaCard(arduino: Arduino?) {
    val texto = obtenerTextoEstado(arduino)
    val detalle = obtenerDetalleEstado(arduino)
    val color = obtenerColorEstado(arduino)
    val icono = obtenerIconoEstado(arduino)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(color.copy(alpha = 0.12f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icono,
                null,
                Modifier.size(28.dp),
                color
            )
            Column {
                Text(
                    texto,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = color
                )
                if (detalle != null) {
                    Text(
                        detalle,
                        fontSize = 13.sp,
                        color = color.copy(alpha = 0.8f)
                    )
                }
            }
        }
    }
}
