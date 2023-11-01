package com.example.koproto.api.category.service

import com.example.koproto.api.category.controller.request.CategoryCreateRequest
import com.example.koproto.api.category.controller.request.CategoryDeleteRequest
import com.example.koproto.api.category.controller.request.CategoryGetRequest
import com.example.koproto.api.category.controller.request.CategoryUpdateRequest
import com.example.koproto.api.category.controller.response.CategoryData
import com.example.koproto.api.category.controller.response.CategoryPageData
import com.example.koproto.api.category.controller.response.CategorySimpleData

interface CategoryService {
    fun create(categoryCreateRequest: CategoryCreateRequest): CategoryData
    fun getByName(name: String): CategoryData
    fun getByNames(names: MutableList<String>): MutableList<CategoryData>
    fun getById(id: Long): CategoryData
    fun getByIds(ids: MutableList<Long>): MutableList<CategoryData>
    fun get(categoryGetRequest: CategoryGetRequest): CategoryPageData
    fun getSubCategoriesByParentId(categoryGetRequest: CategoryGetRequest): CategoryPageData
    fun getSubCategoryNamesRecursiveByName(name: String): MutableList<CategorySimpleData>
    fun getSubCategoryNamesRecursiveById(id: Long): MutableList<CategorySimpleData>
    fun update(categoryUpdateRequest: CategoryUpdateRequest): CategoryData
    fun delete(categoryDeleteRequest: CategoryDeleteRequest)
}
