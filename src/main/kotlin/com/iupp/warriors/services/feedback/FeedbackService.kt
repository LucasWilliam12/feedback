package com.iupp.warriors.services.feedback

import com.iupp.warriors.dtos.responses.FeedbackResponse
import com.iupp.warriors.models.Feedback

interface FeedbackService {
    fun cadastrar(request: Feedback): Feedback
    fun consultar(id: Long): Feedback
    fun listar(): List<FeedbackResponse>
    fun atualizar(id: Long, request: Feedback): Feedback
    fun deletar(id: Long)
}