package com.example.koproto.api.category.controller.request

import com.example.koproto.api.common.message.BLANK
import jakarta.validation.constraints.NotBlank

data class CategoryCreateRequest(
    val parentId: Long? = null,
    @field:NotBlank(message = "name: $BLANK")
    val name: String,
)
