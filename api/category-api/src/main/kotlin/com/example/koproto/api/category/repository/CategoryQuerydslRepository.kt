package com.example.koproto.api.category.repository

import com.example.koproto.infrastructure.database.entity.Category

interface CategoryQuerydslRepository {
    fun findAllByCategoryIdIn(categoryIds: MutableList<Long>): MutableList<Category>
}