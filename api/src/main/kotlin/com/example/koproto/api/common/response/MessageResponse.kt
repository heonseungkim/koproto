package com.example.koproto.api.common.response

data class MessageResponse(
    val message: String,
) : CommonResponse()

object Message {
    const val SUCCESS = "성공"
}
