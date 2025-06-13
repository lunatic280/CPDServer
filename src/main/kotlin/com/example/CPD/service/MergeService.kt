package com.example.CPD.service

import com.example.CPD.data.*
import com.example.CPD.entity.PythonData
import com.example.CPD.repository.LocationRepository
import com.example.CPD.repository.PulseRepository
import com.example.CPD.repository.PythonDataRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Service
class MergeService(
    private val locationRepository: LocationRepository,
    private val pulseRepository: PulseRepository,
    private val pythonDataRepository: PythonDataRepository
) {

    @Transactional
    fun MergeDataSet(distanceDto: DistanceDto): List<MergeDataSetDto> {
        val findStartTime = distanceDto.startTime
        val findEndTime = distanceDto.endTime

        val locationList = locationRepository.findAllByTimestampBetween(findStartTime, findEndTime).map {
            location -> LocationDto.fromEntity(location)
        }

        val pulseList = pulseRepository.findAllByTimeStampBetween(findStartTime, findEndTime).map {
            pulse -> PulseDto.fromEntity(pulse)
        }

        val pythonDateList = pythonDataRepository.findAllByTimestampBetween(findStartTime, findEndTime).map {
            pythonData -> PythonDataDto.fromEntity(pythonData)
        }

        fun LocalDateTime.toMinute(): LocalDateTime = this.truncatedTo(ChronoUnit.MINUTES)

        val locationMap = locationList.groupBy { it.timestamp.toMinute() }
            .mapValues { it.value.firstOrNull() }
        val pulseMap = pulseList.groupBy { it.timeStamp.toMinute() }
            .mapValues { it.value.firstOrNull() }
        val pythonDataMap = pythonDateList.groupBy { it.timestamp.toMinute() }
            .mapValues { it.value.firstOrNull() }

        val allTimeKeys = mutableSetOf<LocalDateTime>()
        allTimeKeys.addAll(locationMap.keys)
        allTimeKeys.addAll(pulseMap.keys)
        allTimeKeys.addAll(pythonDataMap.keys)

        return allTimeKeys.sorted().map { timeKey ->
            MergeDataSetDto(
                time = timeKey,
                latitude = locationMap[timeKey]?.latitude,
                longitude = locationMap[timeKey]?.longitude,
                pythonValue = pythonDataMap[timeKey]?.intTest,
                pulse = pulseMap[timeKey]?.signal

            )
        }


    }
}