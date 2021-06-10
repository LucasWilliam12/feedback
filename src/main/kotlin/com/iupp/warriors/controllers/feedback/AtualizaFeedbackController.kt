package com.iupp.warriors.controllers.feedback

import com.iupp.warriors.dtos.requests.FeedbackRequest
import com.iupp.warriors.dtos.responses.FeedbackResponse
import com.iupp.warriors.repositories.FuncionarioRepository
import com.iupp.warriors.services.feedback.FeedbackService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Put
import io.micronaut.validation.Validated
import javax.inject.Inject
import javax.validation.Valid

@Controller("/feedbacks")
@Validated
class AtualizaFeedbackController(
    @Inject val feedbackService: FeedbackService,
    @Inject val funcionarioRepository: FuncionarioRepository
){

    @Put("/{id}")
    fun atualizar(@PathVariable id: Long, @Valid @Body request: FeedbackRequest): HttpResponse<FeedbackResponse>{

        val feedback = feedbackService.atualizar(id, request.toModel(funcionarioRepository))
        return HttpResponse.ok(FeedbackResponse(feedback))

    }

}