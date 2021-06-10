package com.iupp.warriors.controllers.feedback

import com.iupp.warriors.dtos.responses.FeedbackResponse
import com.iupp.warriors.services.feedback.FeedbackService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import javax.inject.Inject

@Controller("/feedbacks")
class RetornaFeedbackController(@Inject val feedbackService: FeedbackService) {

    @Get("/{id}")
    fun consultar(@PathVariable id: Long): HttpResponse<Any>{
        val feedback = feedbackService.consultar(id)
        return HttpResponse.ok(FeedbackResponse(feedback))
    }

    @Get("/listar")
    fun listar(): HttpResponse<List<FeedbackResponse>>{
        return HttpResponse.ok(feedbackService.listar())
    }

}