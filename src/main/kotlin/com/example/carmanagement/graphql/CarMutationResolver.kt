package com.example.carmanagement.graphql

import com.example.carmanagement.model.Car
import com.example.carmanagement.service.CarService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller

data class CarInput(val brand: String, val model: String, val color: String, val year: Int, val price: Float)
data class UpdateCarInput(val id: Long, val brand: String?, val model: String?, val color: String?, val year: Int?, val price: Float?)

@Controller
class CarMutationResolver(private val carService: CarService) {

    @MutationMapping
    fun addCar(@Argument("car") car: CarInput): Car {
        val newCar = Car(
            brand = car.brand,
            model = car.model,
            color = car.color,
            year = car.year,
            price = car.price
        )
        return carService.addCar(newCar)
    }

    @MutationMapping
    fun updateCar(@Argument("car") car: UpdateCarInput): Car? {
        val updatedCar = Car(
            brand = car.brand ?: "",
            model = car.model ?: "",
            color = car.color ?: "",
            year = car.year ?: 0,
            price = car.price ?: 0f
        )
        return carService.updateCar(car.id, updatedCar)
    }

    @MutationMapping
    fun deleteCar(@Argument("id") id: Long): Boolean {
        return carService.deleteCar(id)
    }
}
