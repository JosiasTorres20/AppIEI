package com.example.myapplication.Firebase

import com.example.myapplication.Firebase.models.Arduino
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MonitorRepository {
    private val db = FirebaseDatabase.getInstance()
    private val _arduinoData = MutableStateFlow<Arduino?>(null)
    val arduinoData: StateFlow<Arduino?> = _arduinoData

    private var listener: ValueEventListener? = null

    fun iniciarEscucha() {
        if (listener == null) {
            listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    _arduinoData.value = snapshot.getValue(Arduino::class.java)
                }
                override fun onCancelled(error: DatabaseError) {
                    _arduinoData.value = null
                }
            }
            db.reference.child("sensor_data").child("arduino")
                .addValueEventListener(listener!!)
        }
    }

    fun detenerEscucha() {
        listener?.let {
            db.reference.child("sensor_data").child("arduino")
                .removeEventListener(it)
            listener = null
        }
    }
}


