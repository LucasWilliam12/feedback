package com.iupp.warriors.infrastructure.model.feedback

import java.time.LocalDateTime
import java.util.*

data class FeedbackEvent (
    var descricao: String? = null,
    var titulo: String? = null,
    var id: UUID? = null,
    var createdAt: LocalDateTime? = LocalDateTime.now()
)