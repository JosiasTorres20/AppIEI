package com.example.myapplication.Firebase.models

data class Arduino(
    val nombreDispositivo: String = "",
    val pulso: Double = 0.0,
    val caida: Boolean = false,
    val timestamp: Long = 0L
)
