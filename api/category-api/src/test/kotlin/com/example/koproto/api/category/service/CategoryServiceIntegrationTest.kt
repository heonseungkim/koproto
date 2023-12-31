package com.example.koproto.api.category.service

import com.example.koproto.api.category.controller.request.CategoryCreateRequest
import com.example.koproto.api.category.controller.request.CategoryDeleteRequest
import com.example.koproto.api.category.controller.request.CategoryGetRequest
import com.example.koproto.api.category.controller.request.CategoryUpdateRequest
import com.example.koproto.api.category.exception.CategoryException
import com.example.koproto.api.category.exception.MSG_CATEGORY_ALREADY_EXISTS
import com.example.koproto.api.category.exception.MSG_CATEGORY_NOT_EXISTS
import com.example.koproto.api.category.service.configuration.ServiceTest
import com.example.koproto.infrastructure.database.fixture.fixtureCategoryAll
import com.example.koproto.infrastructure.database.fixture.fixtureCategoryTop
import com.example.koproto.infrastructure.database.fixture.fixtureCategoryBeverageRecursiveSubCategories
import com.example.koproto.infrastructure.database.fixture.fixtureCategoryBeverageSubCategories
import com.example.koproto.infrastructure.database.fixture.fixtureCategoryHalfTshirt
import com.example.koproto.infrastructure.database.fixture.fixtureCategoryLongTshirt
import com.example.koproto.infrastructure.database.fixture.fixtureCategorySweat
import com.example.koproto.infrastructure.database.fixture.newCategory
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@ServiceTest
class CategoryServiceIntegrationTest(
    @Autowired
    val categoryService: CategoryService,
) {

    @Test
    fun 카테고리_이름이_주어질_때_저장에_성공한다() {
        // given
        val givenName = newCategory().name
        val givenCategoryCreateRequest = CategoryCreateRequest(name = givenName)
        // when & then
        assertDoesNotThrow { categoryService.create(givenCategoryCreateRequest) }
    }

    @Test
    fun 카테고리_이름과_부모_카테고리_id가_주어질_때_저장에_성공한다() {
        // given
        val givenName = newCategory().name
        val givenParentId = fixtureCategoryTop.id
        val givenCategoryCreateRequest = CategoryCreateRequest(name = givenName, parentId = givenParentId)
        // when
        val categoryData = categoryService.create(givenCategoryCreateRequest)
        // then
        assertAll(
            { assertEquals(givenName, categoryData.name) },
            { assertEquals(givenParentId, categoryData.parent!!.id) }
        )
    }

    @Test
    fun 이미_등록된_카테고리_이름으로_저장에_실패한다() {
        // given
        val givenName = fixtureCategoryTop.name
        val givenCategoryCreateRequest = CategoryCreateRequest(name = givenName)
        // when
        val assertThrows = assertThrows(
            CategoryException::class.java
        ) { categoryService.create(givenCategoryCreateRequest) }
        // then
        assertEquals(assertThrows.message, MSG_CATEGORY_ALREADY_EXISTS)
    }

    @Test
    fun 카테고리_이름이_주어질_때_조회에_성공한다() {
        // given
        val givenName = fixtureCategoryTop.name
        // when
        val foundCategory = categoryService.getByName(givenName)
        // then
        assertAll(
            { assertNotNull(foundCategory) },
            { assertEquals(givenName, foundCategory.name) },
        )
    }

    @Test
    fun 존재하지_않는_카테고리_이름으로_조회에_실패한다() {
        // given
        val givenName = newCategory().name
        // when
        val assertThrows = assertThrows(
            CategoryException::class.java,
        ) { categoryService.getByName(givenName) }
        // then
        assertEquals(assertThrows.message, MSG_CATEGORY_NOT_EXISTS)
    }

    @Test
    fun 카테고리_id가_주어질_때_조회에_성공한다() {
        // given
        val givenId = fixtureCategoryTop.id!!
        // when
        val foundCategory = categoryService.getById(givenId)
        // then
        assertAll(
            { assertNotNull(foundCategory) },
            { assertEquals(givenId, foundCategory.id) },
        )
    }

    @Test
    fun 존재하지_않는_카테고리_id로_조회에_실패한다() {
        // given
        val givenId = -1L
        // when
        val assertThrows = assertThrows(
            CategoryException::class.java
        ) { categoryService.getById(givenId) }
        // then
        assertEquals(assertThrows.message, MSG_CATEGORY_NOT_EXISTS)
    }

    @Test
    fun 여러개의_카테고리_id가_주어질_때_조회에_성공한다() {
        // given
        val givenId1 = fixtureCategoryHalfTshirt.id!!
        val givenId2 = fixtureCategoryLongTshirt.id!!
        // when
        val foundCategories = categoryService.getByIds(mutableListOf(givenId1, givenId2))
        // then
        assertAll(
            { assertNotNull(foundCategories) },
            { assertEquals(2, foundCategories.size) },
        )
    }

    @Test
    fun 페이지_정보가_주어질_때_페이지_조회에_성공한다() {
        // given
        val givenPage = 1
        val givenSize = 10
        val givenCategories = fixtureCategoryAll.map { it.name }
        val categoryGetRequest = CategoryGetRequest(page = givenPage, size = givenSize)
        // when
        val page = categoryService.get(categoryGetRequest)
        println(page.list)
        // then
        assertEquals(givenCategories.size, page.list.size)
    }

    @Test
    fun 특정_카테고리_아이디_하위의_카테고리를_조회한다() {
        // given
        val givenParentId = fixtureCategoryTop.id!!
        val givenSubCategoryNames = fixtureCategoryBeverageSubCategories.map { it.name }
        // when
        val subCategoriesPageData = categoryService.getSubCategoriesByParentId(
            CategoryGetRequest(parentId = givenParentId, page = 1, size = 10)
        )
        val subCategoryNames = subCategoriesPageData.list.map { it.name }
        // then
        assertAll(
            { assertEquals(givenSubCategoryNames.size, subCategoryNames.size) },
            { assertTrue(givenSubCategoryNames.containsAll(subCategoryNames)) },
            { assertTrue(subCategoryNames.containsAll(givenSubCategoryNames)) },
        )
    }

    @Test
    fun 특정_카테고리명_하위의_모든_카테고리를_재귀_조회한다() {
        // given
        val givenMainCategoryName = fixtureCategoryTop.name
        val givenRecursiveSubCategoryNames = fixtureCategoryBeverageRecursiveSubCategories.map { it.name }
        // when
        val subCategories = categoryService.getSubCategoryNamesRecursiveByName(name = givenMainCategoryName)
        val subCategoryNames = subCategories.map { it.name }
        // then
        assertAll(
            { assertEquals(givenRecursiveSubCategoryNames.size, subCategoryNames.size) },
            { assertTrue(givenRecursiveSubCategoryNames.containsAll(subCategoryNames)) },
            { assertTrue(subCategoryNames.containsAll(givenRecursiveSubCategoryNames)) },
        )
    }

    @Test
    fun 특정_카테고리_아이디_하위의_모든_카테고리를_재귀_조회한다() {
        // given
        val givenMainCategoryId = fixtureCategoryTop.id!!
        val givenRecursiveSubCategoryNames = fixtureCategoryBeverageRecursiveSubCategories.map { it.name }
        // when
        val subCategories = categoryService.getSubCategoryNamesRecursiveById(id = givenMainCategoryId)
        println(subCategories)
        val subCategoryNames = subCategories.map { it.name }
        // then
        assertAll(
            { assertEquals(givenRecursiveSubCategoryNames.size, subCategoryNames.size) },
            { assertTrue(givenRecursiveSubCategoryNames.containsAll(subCategoryNames)) },
            { assertTrue(subCategoryNames.containsAll(givenRecursiveSubCategoryNames)) },
        )
    }

    @Test
    fun 변경할_카테고리_이름이_주어질_때_변경에_성공한다() {
        // given
        val givenName = newCategory().name
        val categoryUpdateRequest = CategoryUpdateRequest(
            id = fixtureCategoryTop.id!!,
            name = givenName
        )
        // when
        val categoryData = categoryService.update(categoryUpdateRequest)
        // then
        assertEquals(givenName, categoryData.name)
    }

    @Test
    fun 여러개의_카테고리_id가_주어질_때_삭제에_성공한다() {
        // given
        val categoryDeleteRequest = CategoryDeleteRequest(
            ids = mutableListOf(
                fixtureCategorySweat.id!!,
                fixtureCategoryHalfTshirt.id!!,
                fixtureCategoryLongTshirt.id!!,
            )
        )
        // when & then
        assertDoesNotThrow { categoryService.delete(categoryDeleteRequest) }
    }
}
