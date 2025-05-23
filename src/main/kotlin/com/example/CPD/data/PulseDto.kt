package com.example.CPD.data

data class PulseDto(
    val beatsPerMinute: Int,
    val interBeatInterval: Int,
    val signal: Int,
    val timeStamp: String
)
