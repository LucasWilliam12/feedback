package com.iupp.warriors.database.service

import com.iupp.warriors.core.mapper.FeedbackConverter
import com.iupp.warriors.core.model.Feedback
import com.iupp.warriors.core.ports.FeedbackEntityServicePort
import com.iupp.warriors.database.entity.FeedbackEntity
import com.iupp.warriors.database.repository.feedback.FeedbackEntityRepository
import org.slf4j.LoggerFactory
import java.util.*
import javax.inject.Singleton

@Singleton
class FeedbackEntityService(private val feedbackEntityRepository: FeedbackEntityRepository): FeedbackEntityServicePort {
    val logger = LoggerFactory.getLogger(FeedbackEntityService::class.java)
    override fun save(feedbackEntity: FeedbackEntity): Feedback {
        logger.info("Recebendo um feedback para ser entregue ao repository para ser salvo.")
        val feedbackEntityResult = feedbackEntityRepository.saveCql(feedbackEntity)
        return FeedbackConverter.feedbackEntityToFeedback(feedbackEntityResult)
    }

    override fun update(feedbackEntity: FeedbackEntity): Feedback {
        logger.info("Recebendo um feedback para ser entregue ao repository para ser atualizado.")
        val feedbackEntityResult = feedbackEntityRepository.updateCql(feedbackEntity)
        return FeedbackConverter.feedbackEntityToFeedback(feedbackEntityResult)
    }

    override fun delete(id: UUID) {
        logger.info("Recebendo um feedback para ser entregue ao repository para ser deletado.")
        feedbackEntityRepository.deleteCql(id)
    }

}