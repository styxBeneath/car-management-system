package com.example.carmanagement.repository

import com.example.carmanagement.model.Car
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface CarRepository : JpaRepository<Car, Long> {
    override fun findById(id: Long): Optional<Car>
    override fun findAll(): List<Car>
}
