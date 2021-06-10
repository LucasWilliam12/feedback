package com.iupp.warriors.controllers.feedback

import com.iupp.warriors.services.feedback.FeedbackService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.PathVariable
import javax.inject.Inject

@Controller("/feedbacks")
class DeletaFeedbackController(@Inject val feedbackService: FeedbackService) {

    @Delete("/{id}")
    fun deletar(@PathVariable id: Long): HttpResponse<Any>{
        feedbackService.deletar(id)
        return HttpResponse.ok()
    }

}