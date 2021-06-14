package com.iupp.warriors.controllers.handler

import java.time.LocalDateTime

data class HandlerDto(
    val message: String,
    val timestamp: LocalDateTime = LocalDateTime.now()
)