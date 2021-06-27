package com.iupp.warriors.infrastracture.models.feedback

import java.time.LocalDateTime
import java.util.*

class FeedbackEvent (
    var descricao: String,
    var titulo: String,
    var id: UUID? = null,
    var createdAt: LocalDateTime? = LocalDateTime.now()
)