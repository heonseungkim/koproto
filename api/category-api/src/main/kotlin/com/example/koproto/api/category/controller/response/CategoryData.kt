package com.example.koproto.api.category.controller.response

import com.example.koproto.api.common.paging.Paging

data class CategoryData(
    val id: Long,
    val name: String,
    val parent: CategorySimpleData? = null,
    val subCategories: MutableList<CategorySimpleData>,
)

data class CategorySimpleData(
    val id: Long,
    val name: String,
)

data class CategoryPageData(
    val paging: Paging,
    val list: MutableList<CategoryData>,
)
