package com.iupp.warriors.core.service

import com.iupp.warriors.core.mapper.FeedbackConverter
import com.iupp.warriors.core.model.Feedback
import com.iupp.warriors.core.ports.FeedbackEntityServicePort
import com.iupp.warriors.core.ports.FeedbackServicePort
import com.iupp.warriors.infrastructure.models.feedback.FeedbackEvent
import java.util.*
import javax.inject.Singleton

@Singleton
class FeedbackService(private val service: FeedbackEntityServicePort): FeedbackServicePort {
    override fun insert(feedback: Feedback): FeedbackEvent {
        val response = service.save(FeedbackConverter.feedbackToFeedbackEntity(feedback))
        return FeedbackConverter.feedbackToFeedbackEvent(response);
    }

    override fun update(feedback: Feedback): FeedbackEvent {
        val response = service.update(FeedbackConverter.feedbackToFeedbackEntity(feedback));
        return FeedbackConverter.feedbackToFeedbackEvent(response)
    }

    override fun delete(id: UUID) {
        service.delete(id)
    }
}