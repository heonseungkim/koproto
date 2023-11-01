package com.example.koproto.api.category.controller.request

data class CategoryGetRequest(
    var parentId: Long? = null,
    var page: Int,
    var size: Int,
)
