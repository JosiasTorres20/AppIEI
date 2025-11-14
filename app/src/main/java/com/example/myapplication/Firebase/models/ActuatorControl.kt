package com.example.myapplication.Firebase.models

data class ActuatorControl(
    val activo: Boolean = false,
    val last_update: Long = 0L,
    val modo: String = "manual"
)
