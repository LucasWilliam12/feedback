package com.iupp.warriors.infrastructure.subscribers

import com.iupp.warriors.core.mappers.FeedbackConverter
import com.iupp.warriors.core.ports.FeedbackServicePort
import com.iupp.warriors.infrastructure.models.Events
import com.iupp.warriors.infrastructure.models.feedback.EventsInformation
import io.micronaut.nats.annotation.NatsListener
import io.micronaut.nats.annotation.Subject
import org.slf4j.LoggerFactory

@NatsListener
class FeedbackServer(private val service: FeedbackServicePort) {
    val logger = LoggerFactory.getLogger(FeedbackServer::class.java)

    @Subject("my-feedbacks")
    fun receive(events: EventsInformation){
        if(events.event.name == Events.SAVE_PRODUCT.name){
            logger.info("Recebendo informações para salvar um feedback")
            val response = service.insert(FeedbackConverter.feedbackEventDtoToFeedback(events.feedbackEvent))
            logger.info("Feedback salvo no banco: $response")
        }

        if(events.event.name == Events.UPDATE_PRODUCT.name){
            logger.info("Recebendo informações de um feedback para ser atualizado: ${events.feedbackEvent}")
            val response = service.update(FeedbackConverter.feedbackEventDtoToFeedback(events.feedbackEvent))
            logger.info("Feedback atualizado no banco: $response")
        }

        if(events.event.name == Events.DELETE_PRODUCT.name){
            logger.info("Recebendo informações de um feedback para ser deletado: ${events.feedbackEvent}")
            val response = service.delete(FeedbackConverter.feedbackEventDtoToFeedback(events.feedbackEvent))
            logger.info("Feedback deletado do banco")
        }
    }
}