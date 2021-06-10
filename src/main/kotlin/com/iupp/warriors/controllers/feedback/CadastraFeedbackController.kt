package com.iupp.warriors.controllers.feedback

import com.iupp.warriors.dtos.requests.FeedbackRequest
import com.iupp.warriors.models.Feedback
import com.iupp.warriors.repositories.FuncionarioRepository
import com.iupp.warriors.services.feedback.FeedbackService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import javax.inject.Inject
import javax.validation.Valid

@Validated
@Controller("/feedbacks")
class CadastraFeedbackController(
    @Inject val funcionarioRepository: FuncionarioRepository,
    @Inject val feedbackService: FeedbackService
){

    @Post
    fun cadastrar(@Valid request: FeedbackRequest): HttpResponse<Feedback>{
        var feedback = request.toModel(funcionarioRepository)
        feedback = feedbackService.cadastrar(feedback)
        val uri = UriBuilder.of("/feedbacks/{id}")
            .expand(mutableMapOf(Pair("id", feedback.id)))
        return HttpResponse.created(uri)
    }

}