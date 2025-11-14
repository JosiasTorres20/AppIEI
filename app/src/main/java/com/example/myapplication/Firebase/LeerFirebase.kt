package com.example.myapplication.Firebase


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.myapplication.Firebase.models.Arduino


const val UMBRAL_PULSO_CAIDA = 95.0

fun detectarCaida(inclinacion: Boolean?, pulso: Double?): String {
    if (inclinacion == true && pulso != null && pulso >= UMBRAL_PULSO_CAIDA) {
        return "CAIDA_CONFIRMADA"
    }
    if (inclinacion == true) {
        return "ALERTA_INCLINACION"
    }
    return "SIN_CAIDA"
}

fun obtenerTextoEstado(arduino: Arduino?): String {
    if (arduino == null) return "Cargando..."

    return when (detectarCaida(arduino.caida, arduino.pulso)) {
        "CAIDA_CONFIRMADA" -> "Caída confirmada"
        "ALERTA_INCLINACION" -> "Posible caída"
        else -> "Sin caídas"
    }
}

fun obtenerDetalleEstado(arduino: Arduino?): String? {
    if (arduino == null) return null

    val resultado = detectarCaida(arduino.caida, arduino.pulso)
    val pulsoAlto = arduino.pulso != null && arduino.pulso >= UMBRAL_PULSO_CAIDA

    return when (resultado) {
        "CAIDA_CONFIRMADA" -> "Pulso ${arduino.pulso?.toInt()} BPM"
        "ALERTA_INCLINACION" -> "Verificando pulso..."
        else -> if (pulsoAlto) "Alerta de pulsaciones: ${arduino.pulso?.toInt()} BPM" else "Pulso estable"
    }
}

fun obtenerColorEstado(arduino: Arduino?): Color {
    if (arduino == null) return Color.Gray

    val resultado = detectarCaida(arduino.caida, arduino.pulso)
    val pulsoAlto = arduino.pulso != null && arduino.pulso >= UMBRAL_PULSO_CAIDA

    return when (resultado) {
        "CAIDA_CONFIRMADA" -> Color(0xFFFF5722)
        "ALERTA_INCLINACION" -> Color(0xFFFFA000)
        else -> if (pulsoAlto) Color(0xFFFFA000) else Color(0xFF4CAF50)
    }
}

fun obtenerIconoEstado(arduino: Arduino?): ImageVector {
    if (arduino == null) return Icons.Default.CheckCircle

    return when (detectarCaida(arduino.caida, arduino.pulso)) {
        "CAIDA_CONFIRMADA" -> Icons.Default.Warning
        "ALERTA_INCLINACION" -> Icons.Default.Info
        else -> Icons.Default.CheckCircle
    }
}

