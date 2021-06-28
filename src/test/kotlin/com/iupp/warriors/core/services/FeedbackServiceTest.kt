package com.iupp.warriors.core.services

import com.iupp.warriors.core.mappers.FeedbackConverter
import com.iupp.warriors.core.models.Feedback
import com.iupp.warriors.core.ports.FeedbackEntityServicePort
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import java.util.*

@MicronautTest
internal class FeedbackServiceTest: AnnotationSpec(){

    lateinit var service: FeedbackService
    lateinit var entityService: FeedbackEntityServicePort

    @BeforeEach
    fun setUp(){
        entityService = mockk<FeedbackEntityServicePort>()
        service = FeedbackService(entityService)
    }

    @Test
    fun `deve cadastrar um novo feedback`(){
        val feedback = Feedback("Descricao Teste", "Titulo Teste")
        every { entityService.save(any()) } answers {
            feedback.id = UUID.randomUUID()
            feedback
        }
        val response = service.insert(feedback)
        response shouldBe FeedbackConverter.feedbackToFeedbackEvent(feedback)
    }

    @Test
    fun `Deve atualizar um feedback`(){
        val feedback = Feedback("Descricao Teste", "Titulo Teste", UUID.randomUUID())
        every { entityService.update(any()) } answers {
            feedback
        }
        val response = service.update(feedback)
        response shouldBe FeedbackConverter.feedbackToFeedbackEvent(feedback)
    }

    @Test
    fun `deve deletar um feedback`(){
        val feedback = Feedback(id = UUID.randomUUID())
        every { entityService.delete(any()) } returns Unit

        val response = service.delete(feedback)
        response shouldBe Unit
    }
}