package com.example.myapplication.Firebase

import com.example.myapplication.Firebase.models.RegistroCaida
import com.google.firebase.database.FirebaseDatabase

class EscribirFirebase {
    private val db = FirebaseDatabase.getInstance()

    fun escribirArduinoSimulado(pulso: Double, inclinacion: Boolean) {
        val ref = db.reference.child("sensor_data").child("arduino")
        ref.child("pulso").setValue(pulso)
        ref.child("caida").setValue(inclinacion)
    }

    fun guardarRegistroCaida(
        pulso: Double,
        caida: Boolean = true,
        nota: String? = null
    ) {
        // Solo guardar si hay una caída real
        if (!caida) return

        val ref = db.reference.child("sensor_data").child("fall_history")
        val key = ref.push().key ?: return

        val evento = RegistroCaida(
            id = key,
            timestamp = System.currentTimeMillis(),
            pulso = pulso,
            caida = caida,
            nota = nota
        )

        ref.child(key).setValue(evento)
    }
}
