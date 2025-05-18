package com.example.CPD.controller

import com.example.CPD.data.PythonDataDto
import com.example.CPD.entity.PythonData
import com.example.CPD.service.PythonDataService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/test")
class JsonController(
    private val pythonDataService: PythonDataService
) {

    @PostMapping("/test-json")
    fun postJson(@RequestBody pythonDto: PythonDataDto): ResponseEntity<String> {
        val savedData = pythonDataService.save(pythonDto)
        return ResponseEntity.ok(savedData.toString())
    }
}