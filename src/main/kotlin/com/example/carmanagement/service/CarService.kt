package com.example.carmanagement.service

import com.example.carmanagement.graphql.CarSubscriptionResolver
import com.example.carmanagement.model.Car
import com.example.carmanagement.repository.CarRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class CarService(
    private val carRepository: CarRepository,
    private val carSubscriptionResolver: CarSubscriptionResolver
) {

    fun getAllCars(): List<Car> = carRepository.findAll()

    fun getCarById(id: Long): Optional<Car> = carRepository.findById(id)

    fun addCar(car: Car): Car {
        val savedCar = carRepository.save(car)
        carSubscriptionResolver.notifyCarAdded(savedCar)
        return savedCar
    }

    fun updateCar(id: Long, car: Car): Car? {
        return if (carRepository.findById(id).isPresent) {
            carRepository.save(car)
            carSubscriptionResolver.notifyCarUpdated(car)
            car
        } else {
            null
        }
    }

    fun deleteCar(id: Long): Boolean {
        return if (carRepository.existsById(id)) {
            carRepository.deleteById(id)
            true
        } else {
            false
        }
    }
}
