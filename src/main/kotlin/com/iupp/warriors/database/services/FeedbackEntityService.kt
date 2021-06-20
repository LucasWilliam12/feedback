package com.iupp.warriors.database.services

import com.iupp.warriors.core.mappers.FeedbackConverter
import com.iupp.warriors.core.models.Feedback
import com.iupp.warriors.core.ports.FeedbackEntityServicePort
import com.iupp.warriors.database.entity.FeedbackEntity
import com.iupp.warriors.database.repository.feedback.FeedbackEntityRepository
import javax.inject.Singleton

@Singleton
class FeedbackEntityService(private val feedbackEntityRepository: FeedbackEntityRepository): FeedbackEntityServicePort {
    override fun save(feedbackEntity: FeedbackEntity): Feedback {
        val feedbackEntityResult = feedbackEntityRepository.saveCql(feedbackEntity)
        return FeedbackConverter.feedbackEntityToFeedback(feedbackEntityResult)
    }

}