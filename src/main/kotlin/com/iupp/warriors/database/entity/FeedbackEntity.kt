package com.iupp.warriors.database.entity

import io.micronaut.core.annotation.Introspected
import java.time.LocalDateTime
import java.util.*

@Introspected
data class FeedbackEntity (
    var descricao: String,
    var titulo: String,
    var id: UUID? = null,
    var avaliado: UUID,
    var avaliador: UUID,
    var createdAt: LocalDateTime? = LocalDateTime.now()
)