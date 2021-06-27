package com.iupp.warriors.core.mappers

import com.iupp.warriors.core.models.Feedback
import com.iupp.warriors.database.entity.FeedbackEntity
import com.iupp.warriors.infrastracture.models.feedback.FeedbackEvent

class FeedbackConverter {
    companion object{
        fun feedbackEntityToFeedback(feedbackEntity: FeedbackEntity): Feedback = Feedback(
            feedbackEntity.descricao,
            feedbackEntity.titulo,
            feedbackEntity.id,
            feedbackEntity.createdAt
        )

        fun feedbackToFeedbackEntity(feedback: Feedback): FeedbackEntity = FeedbackEntity(
            feedback.descricao,
            feedback.titulo,
            feedback.id,
            feedback.createdAt
        )

        fun feedbackToFeedbackEvent(feedback: Feedback): FeedbackEvent = FeedbackEvent(
            feedback.descricao,
            feedback.titulo,
            feedback.id,
            feedback.createdAt
        )

        fun feedbackEventDtoToFeedback(feedback: FeedbackEvent): Feedback = Feedback(
            feedback.descricao,
            feedback.titulo,
            feedback.id,
            feedback.createdAt
        )
    }
}