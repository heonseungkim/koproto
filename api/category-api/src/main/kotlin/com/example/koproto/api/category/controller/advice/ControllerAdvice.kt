package com.example.koproto.api.category.controller.advice

import com.example.koproto.api.common.advice.CommonControllerAdvice
import com.example.koproto.api.common.response.ErrorResponse
import com.example.koproto.api.common.response.ErrorType
import com.example.koproto.api.category.exception.CategoryException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerAdvice : CommonControllerAdvice() {

    @ExceptionHandler(CategoryException::class)
    fun handleCategoryException(e: CategoryException): ErrorResponse {
        return ErrorResponse(
            type = ErrorType.CATEGORY_ERROR,
            description = e.message,
        )
    }
}
