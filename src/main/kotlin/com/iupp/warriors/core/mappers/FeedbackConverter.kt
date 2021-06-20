package com.iupp.warriors.core.mappers

import com.iupp.warriors.core.models.Feedback
import com.iupp.warriors.database.entity.FeedbackEntity
import com.iupp.warriors.infrastracture.models.FeedbackEvent

class FeedbackConverter {
    companion object{
        fun feedbackEntityToFeedback(feedbackEntity: FeedbackEntity): Feedback {
            return Feedback(
                feedbackEntity.descricao,
                feedbackEntity.titulo,
                feedbackEntity.id,
                feedbackEntity.avaliado,
                feedbackEntity.avaliador,
                feedbackEntity.createdAt
            )
        }

        fun feedbackToFeedbackEntity(feedback: Feedback): FeedbackEntity {
            return FeedbackEntity(
                feedback.descricao,
                feedback.titulo,
                feedback.id,
                feedback.avaliado,
                feedback.avaliador,
                feedback.createdAt
            )
        }

        fun feedbackToFeedbackEvent(feedback: Feedback): FeedbackEvent {
            return FeedbackEvent(
                feedback.descricao,
                feedback.titulo,
                feedback.id,
                feedback.avaliado,
                feedback.avaliador,
                feedback.createdAt
            )
        }
    }
}