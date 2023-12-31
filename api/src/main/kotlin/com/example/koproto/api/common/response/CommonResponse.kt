package com.example.koproto.api.common.response

import java.time.LocalDateTime

abstract class CommonResponse(
    val time: LocalDateTime = LocalDateTime.now(),
    var status: Int = 200,
)
