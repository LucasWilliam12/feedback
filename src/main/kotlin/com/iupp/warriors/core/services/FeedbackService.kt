package com.iupp.warriors.core.services

import com.iupp.warriors.core.mappers.FeedbackConverter
import com.iupp.warriors.core.models.Feedback
import com.iupp.warriors.core.ports.FeedbackEntityServicePort
import com.iupp.warriors.core.ports.FeedbackServicePort
import com.iupp.warriors.infrastracture.models.FeedbackEvent
import javax.inject.Singleton

@Singleton
class FeedbackService(private val service: FeedbackEntityServicePort): FeedbackServicePort {
    override fun insert(feedback: Feedback): FeedbackEvent {
        val response = service.save(FeedbackConverter.feedbackToFeedbackEntity(feedback))
        return FeedbackConverter.feedbackToFeedbackEvent(response);
    }
}