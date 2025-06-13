package com.example.CPD.data

import java.time.LocalDateTime

data class MergeDataSetDto(
    val time: LocalDateTime?,
    val latitude: Double?,
    val longitude: Double?,
    val pulse: Int?,
    val pythonValue: Int?,
    //val checkingClass: List<Int>
    //val distanceBetweenObject: Double -> 강아지와 객체 간의 거리 아니면 일정 거리내에 들어오면 되도록 라즈베리파이 내에서 설정
)
