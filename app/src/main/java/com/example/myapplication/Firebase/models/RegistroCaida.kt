package com.example.myapplication.Firebase.models

data class RegistroCaida(
    val id: String? = null,
    val timestamp: Long? = null,
    val pulso: Double? = null,
    val caida: Boolean? = null,
    val nota: String? = null
) {
    constructor() : this(null, null, null, null, null)
}
