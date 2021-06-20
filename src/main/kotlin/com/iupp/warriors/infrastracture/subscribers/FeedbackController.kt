package com.iupp.warriors.infrastracture.subscribers

import com.iupp.warriors.core.models.Feedback
import com.iupp.warriors.core.ports.FeedbackServicePort
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post

@Controller("/feedbacks")
class FeedbackController(private val service: FeedbackServicePort) {

    @Post
    fun cadastrar(@Body feedback: Feedback): HttpResponse<Any>{
        val response = service.insert(feedback)
        return HttpResponse.ok(response)
    }

}