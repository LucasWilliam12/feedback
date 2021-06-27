package com.iupp.warriors.database.entity

import io.micronaut.core.annotation.Introspected
import java.time.LocalDateTime
import java.util.*

@Introspected
data class FeedbackEntity (
    var descricao: String,
    var titulo: String,
    var id: UUID? = null,
    var createdAt: LocalDateTime? = LocalDateTime.now()
)