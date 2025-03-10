package com.example.carmanagement.graphql

import com.example.carmanagement.model.Car
import com.example.carmanagement.service.CarService
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import java.util.*

@Controller
class CarQueryResolver(private val carService: CarService) {

    @QueryMapping
    fun allCars(): List<Car> {
        return carService.getAllCars()
    }

    @QueryMapping
    fun carById(id: Long): Optional<Car> {
        return carService.getCarById(id)
    }
}
