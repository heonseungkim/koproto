package com.example.koproto.api.category.repository

import com.example.koproto.infrastructure.database.entity.Category
import com.example.koproto.infrastructure.database.entity.QCategory
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Autowired

class CategoryQuerydslRepositoryImpl(
    @Autowired
    private val jpaQueryFactory: JPAQueryFactory,
) : CategoryQuerydslRepository {

    override fun findAllByCategoryIdIn(categoryIds: MutableList<Long>): MutableList<Category> {
        val qCategory = QCategory.category
        return jpaQueryFactory
            .select(qCategory)
            .from(qCategory)
            .where(qCategory.id.`in`(categoryIds))
            .fetch()
    }
}