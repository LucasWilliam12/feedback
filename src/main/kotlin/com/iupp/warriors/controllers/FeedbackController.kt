package com.iupp.warriors.controllers

import com.iupp.warriors.dtos.requests.FeedbackRequest
import com.iupp.warriors.dtos.responses.FeedbackResponse
import com.iupp.warriors.models.Feedback
import com.iupp.warriors.repositories.FuncionarioRepository
import com.iupp.warriors.services.feedback.FeedbackService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import javax.validation.Valid

@Controller("/feedbacks")
@Validated
class FeedbackController(
    private val feedbackService: FeedbackService,
    private val funcionarioRepository: FuncionarioRepository
) {
    @Post
    fun cadastrar(@Valid request: FeedbackRequest): HttpResponse<Feedback>{
        var feedback = request.toModel(funcionarioRepository)
        feedback = feedbackService.cadastrar(feedback)
        val uri = UriBuilder.of("/feedbacks/{id}")
            .expand(mutableMapOf(Pair("id", feedback.id)))
        return HttpResponse.created(uri)
    }

    @Put("/{id}")
    fun atualizar(@PathVariable id: Long, @Valid @Body request: FeedbackRequest): HttpResponse<FeedbackResponse> {

        val feedback = feedbackService.atualizar(id, request.toModel(funcionarioRepository))
        return HttpResponse.ok(FeedbackResponse(feedback))

    }

    @Delete("/{id}")
    fun deletar(@PathVariable id: Long): HttpResponse<Any>{
        feedbackService.deletar(id)
        return HttpResponse.ok()
    }

    @Get("/{id}")
    fun consultar(@PathVariable id: Long): HttpResponse<FeedbackResponse>{
        val feedback = feedbackService.consultar(id)
        return HttpResponse.ok(FeedbackResponse(feedback))
    }

    @Get("/listar")
    fun listar(): HttpResponse<List<FeedbackResponse>>{
        return HttpResponse.ok(feedbackService.listar())
    }
}