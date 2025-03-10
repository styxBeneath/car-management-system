package com.example.carmanagement.exception

import graphql.GraphQLError
import graphql.GraphqlErrorBuilder
import jakarta.persistence.EntityNotFoundException
import jakarta.validation.ConstraintViolationException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler
import org.springframework.http.HttpStatus
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.util.*

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(value = [EntityNotFoundException::class])
    fun handleEntityNotFoundException(ex: EntityNotFoundException): GraphQLError {
        return GraphqlErrorBuilder.newError()
            .message(ex.message ?: "Requested entity was not found.")
            .errorType(graphql.ErrorType.DataFetchingException)
            .build()
    }

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun handleValidationException(ex: MethodArgumentNotValidException): GraphQLError {
        val errors = ex.bindingResult.allErrors.mapNotNull { error ->
            if (error is FieldError) {
                "${error.field}: ${error.defaultMessage}"
            } else {
                error.defaultMessage
            }
        }.joinToString(", ")

        return GraphqlErrorBuilder.newError()
            .message("Validation failed: $errors")
            .errorType(graphql.ErrorType.ValidationError)
            .build()
    }

    @ExceptionHandler(value = [ConstraintViolationException::class])
    fun handleConstraintViolationException(ex: ConstraintViolationException): GraphQLError {
        val errors = ex.constraintViolations.joinToString(", ") {
            "${it.propertyPath}: ${it.message}"
        }

        return GraphqlErrorBuilder.newError()
            .message("Constraint violation: $errors")
            .errorType(graphql.ErrorType.ValidationError)
            .build()
    }

    @ExceptionHandler(value = [DataIntegrityViolationException::class])
    fun handleDataIntegrityViolation(ex: DataIntegrityViolationException): GraphQLError {
        return GraphqlErrorBuilder.newError()
            .message("Database error: ${ex.mostSpecificCause.message}")
            .errorType(graphql.ErrorType.DataFetchingException)
            .build()
    }

    @ExceptionHandler(value = [Exception::class])
    fun handleGenericException(ex: Exception): GraphQLError {
        return GraphqlErrorBuilder.newError()
            .message("An unexpected error occurred: ${ex.localizedMessage}")
            .errorType(graphql.ErrorType.DataFetchingException)
            .build()
    }
}
