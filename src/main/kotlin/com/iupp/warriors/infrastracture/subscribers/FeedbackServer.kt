package com.iupp.warriors.infrastracture.subscribers

import com.iupp.warriors.core.mappers.FeedbackConverter
import com.iupp.warriors.core.ports.FeedbackServicePort
import com.iupp.warriors.infrastracture.models.Events
import com.iupp.warriors.infrastracture.models.feedback.EventsInformation
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
            service.insert(FeedbackConverter.feedbackEventDtoToFeedback(events.feedbackEvent))
        }
    }
}