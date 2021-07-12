package com.iupp.warriors.infrastructure.subscriber

import com.iupp.warriors.core.ports.FeedbackServicePort
import com.iupp.warriors.infrastructure.model.Events
import com.iupp.warriors.infrastructure.model.feedback.EventsInformation
import com.iupp.warriors.infrastructure.model.feedback.FeedbackEvent
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import java.util.*

@MicronautTest
internal class FeedbackServerTest: AnnotationSpec(){

    lateinit var feedbackServicePort: FeedbackServicePort
    lateinit var feedbackServer: FeedbackServer

    @BeforeEach
    fun setUp(){
        feedbackServicePort = mockk<FeedbackServicePort>()
        feedbackServer = FeedbackServer(feedbackServicePort)
    }

    @Test
    fun `Deve cadastrar um novo feedback`(){
        val informations = EventsInformation(Events.SAVE_PRODUCT, FeedbackEvent("Descricao teste", "Titulo teste"))
        every { feedbackServicePort.insert(any()) } returns informations.feedbackEvent
        val response = feedbackServer.receive(informations)
        response shouldBe Unit
    }

    @Test
    fun `Deve deletar um feedback`(){
        val informations = EventsInformation(Events.DELETE_PRODUCT, FeedbackEvent(id = UUID.randomUUID()))
        every { feedbackServicePort.delete(any()) } returns Unit
        val response = feedbackServer.receive(informations)
        response shouldBe Unit
    }

    @Test
    fun `Deve atualizar um feedback`(){
        val informations = EventsInformation(Events.UPDATE_PRODUCT, FeedbackEvent("Descricao teste", "Titulo teste", UUID.randomUUID()))
        every { feedbackServicePort.update(any()) } returns informations.feedbackEvent
        val response = feedbackServer.receive(informations)
        response shouldBe Unit
    }

}