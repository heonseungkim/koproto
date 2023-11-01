package com.example.koproto.api.common.response

import com.example.koproto.api.common.paging.Paging

data class PageResponse<T>(
    val paging: Paging,
    val list: MutableList<T>,
) : CommonResponse()
