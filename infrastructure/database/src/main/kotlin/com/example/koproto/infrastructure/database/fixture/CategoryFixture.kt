package com.example.koproto.infrastructure.database.fixture

import com.example.koproto.infrastructure.database.entity.Category

val fixtureCategoryShoes = Category(id = 1000, name = "신발", parent = null)
val fixtureCategoryTop = Category(id = 1001, name = "상의", parent = null)
val fixtureCategoryHat = Category(id = 1002, name = "모자", parent = null)
val fixtureCategoryTshirt = Category(id = 1003, name = "티셔츠", parent = fixtureCategoryTop)
val fixtureCategorySweat = Category(id = 1004, name = "스웨터", parent = fixtureCategoryTop)
val fixtureCategoryHalfTshirt = Category(id = 1005, name = "반팔티", parent = fixtureCategoryTshirt)
val fixtureCategoryLongTshirt = Category(id = 1006, name = "긴팔티", parent = fixtureCategoryTshirt)

val fixtureCategoryBeverageSubCategories = mutableListOf(fixtureCategoryTshirt,
                                                         fixtureCategorySweat)
val fixtureCategoryBeverageRecursiveSubCategories = mutableListOf(fixtureCategoryTop,
                                                                  fixtureCategoryTshirt,
                                                                  fixtureCategorySweat,
                                                                  fixtureCategoryHalfTshirt,
                                                                  fixtureCategoryLongTshirt)
val fixtureCategoryAll = mutableListOf(fixtureCategoryShoes,
                                       fixtureCategoryTop,
                                       fixtureCategoryHat,
                                       fixtureCategoryTshirt,
                                       fixtureCategorySweat,
                                       fixtureCategoryHalfTshirt,
                                       fixtureCategoryLongTshirt)

fun newCategory(): Category {
    return Category(name = "category-${Math.random()}")
}
