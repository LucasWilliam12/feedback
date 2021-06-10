package com.iupp.warriors.dtos.responses

import com.iupp.warriors.models.Feedback

data class FeedbackResponse(
    val titulo: String,
    val descricao: String,
    val avaliado:  FuncionarioResponse,
    val avaliador: FuncionarioResponse
){
    constructor(feedback: Feedback) : this(
        titulo = feedback.titulo,
        descricao = feedback.descricao,
        avaliado = FuncionarioResponse(feedback.avaliado),
        avaliador = FuncionarioResponse(feedback.avaliador),
    )
}