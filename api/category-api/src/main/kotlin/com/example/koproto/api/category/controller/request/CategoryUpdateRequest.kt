package com.example.koproto.api.category.controller.request

import com.example.koproto.api.common.message.BLANK
import jakarta.validation.constraints.NotBlank

data class CategoryUpdateRequest(
    val id: Long,
    @field:NotBlank(message = "name: $BLANK")
    val name: String,
)
