package com.example.carmanagement.service

import com.example.carmanagement.model.Car
import com.example.carmanagement.repository.CarRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.bean.override.mockito.MockitoBean

@SpringBootTest
@ActiveProfiles("test")
class CarServiceTest {

    @MockitoBean
    lateinit var carRepository: CarRepository

    @Autowired
    lateinit var carService: CarService

    @Test
    fun `should add a car`() {
        val car = Car(brand = "Toyota", model = "Camry", color = "Red", year = 2022, price = 30000.0f)
        `when`(carRepository.save(car)).thenReturn(car)

        val result = carService.addCar(car)

        assertNotNull(result)
        assertEquals("Toyota", result.brand)
        assertEquals("Camry", result.model)
    }

    @Test
    fun `should update a car`() {
        val car = Car(id = 1, brand = "Toyota", model = "Corolla", color = "Blue", year = 2021, price = 25000.0f)
        val updatedCar = car.copy(price = 22000.0f)
        `when`(carRepository.findById(car.id!!)).thenReturn(java.util.Optional.of(car))
        `when`(carRepository.save(updatedCar)).thenReturn(updatedCar)

        val result = carService.updateCar(car.id!!, updatedCar)

        assertNotNull(result)
        assertEquals(22000.0f, result?.price)
    }

    @Test
    fun `should delete a car`() {
        val carId = 1L
        `when`(carRepository.existsById(carId)).thenReturn(true)
        doNothing().`when`(carRepository).deleteById(carId)

        val result = carService.deleteCar(carId)

        assertTrue(result)
        verify(carRepository, times(1)).deleteById(carId)
    }

    @Test
    fun `should return null when car not found for update`() {
        val car = Car(id = 1, brand = "Toyota", model = "Corolla", color = "Blue", year = 2021, price = 25000.0f)
        val updatedCar = car.copy(price = 22000.0f)
        `when`(carRepository.findById(car.id!!)).thenReturn(java.util.Optional.empty())

        val result = carService.updateCar(car.id!!, updatedCar)

        assertNull(result)
        verify(carRepository, times(1)).findById(car.id!!)
    }

    @Test
    fun `should return false when car not found for deletion`() {
        val carId = 1L
        `when`(carRepository.existsById(carId)).thenReturn(false)

        val result = carService.deleteCar(carId)

        assertFalse(result)
        verify(carRepository, times(1)).existsById(carId)
    }

    @Test
    fun `should throw exception when car save fails`() {
        val car = Car(brand = "Toyota", model = "Camry", color = "Red", year = 2022, price = 30000.0f)
        `when`(carRepository.save(car)).thenThrow(RuntimeException("Database error"))

        val exception = assertThrows<RuntimeException> { carService.addCar(car) }

        assertEquals("Database error", exception.message)
    }


    @Test
    fun `should update car with optional fields`() {
        val car = Car(id = 1, brand = "Toyota", model = "Corolla", color = "Blue", year = 2021, price = 25000.0f)
        val updatedCar = car.copy(price = 22000.0f)
        `when`(carRepository.findById(car.id!!)).thenReturn(java.util.Optional.of(car))
        `when`(carRepository.save(updatedCar)).thenReturn(updatedCar)

        val result = carService.updateCar(car.id!!, updatedCar)

        assertNotNull(result)
        assertEquals(22000.0f, result?.price)
    }

    @Test
    fun `should return null when car to update is null`() {
        val carId = 1L
        val updatedCar =
            Car(id = carId, brand = "Toyota", model = "Corolla", color = "Blue", year = 2021, price = 22000.0f)
        `when`(carRepository.findById(carId)).thenReturn(java.util.Optional.empty())

        val result = carService.updateCar(carId, updatedCar)

        assertNull(result)
    }
}
