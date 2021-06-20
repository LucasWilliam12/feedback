package com.iupp.warriors.infrastracture.models

import java.time.LocalDateTime
import java.util.*

class FeedbackEvent (
    var descricao: String,
    var titulo: String,
    var id: UUID? = null,
    var avaliado: UUID,
    var avaliador: UUID,
    var createdAt: LocalDateTime? = LocalDateTime.now()
)