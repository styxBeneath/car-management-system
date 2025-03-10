package com.example.carmanagement.graphql

import com.example.carmanagement.model.Car
import org.reactivestreams.Publisher
import org.springframework.graphql.data.method.annotation.SubscriptionMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Sinks

@Controller
class CarSubscriptionResolver {

    private val carAddedSink: Sinks.Many<Car> = Sinks.many().multicast().onBackpressureBuffer()
    private val carUpdatedSink: Sinks.Many<Car> = Sinks.many().multicast().onBackpressureBuffer()

    @SubscriptionMapping
    fun carAdded(): Publisher<Car> {
        return carAddedSink.asFlux()
    }

    @SubscriptionMapping
    fun carUpdated(): Publisher<Car> {
        return carUpdatedSink.asFlux()
    }

    fun notifyCarAdded(car: Car) {
        carAddedSink.tryEmitNext(car)
    }

    fun notifyCarUpdated(car: Car) {
        carUpdatedSink.tryEmitNext(car)
    }
}
