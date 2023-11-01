package com.example.koproto.api.category.exception

val MSG_CATEGORY_NOT_EXISTS = "존재하지 않는 카테고리입니다."
fun categoryDoesNotExists(): CategoryException {
    return CategoryException(MSG_CATEGORY_NOT_EXISTS)
}

val MSG_CATEGORY_ALREADY_EXISTS = "이미 존재하는 카테고리입니다."
fun categoryAlreadyExists(): CategoryException {
    return CategoryException(MSG_CATEGORY_ALREADY_EXISTS)
}
