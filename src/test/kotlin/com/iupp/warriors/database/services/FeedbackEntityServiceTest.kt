package com.iupp.warriors.database.services

import com.iupp.warriors.core.mappers.FeedbackConverter
import com.iupp.warriors.database.entity.FeedbackEntity
import com.iupp.warriors.database.repository.feedback.FeedbackEntityRepository
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import java.util.*

@MicronautTest
internal class FeedbackEntityServiceTest: AnnotationSpec(){

    lateinit var entityService: FeedbackEntityService
    lateinit var entityRepository: FeedbackEntityRepository

    @BeforeEach
    fun setUp(){
        entityRepository = mockk<FeedbackEntityRepository>()
        entityService = FeedbackEntityService(entityRepository)
    }

    @Test
    fun `Deve cadastrar um novo feedback`(){
        val feedbackEntity = FeedbackEntity("Descricao teste", "titulo teste")
        every { entityRepository.saveCql(feedbackEntity) } answers {
            feedbackEntity.id = UUID.randomUUID()
            feedbackEntity
        }
        val response = entityService.save(feedbackEntity)

        response shouldBe FeedbackConverter.feedbackEntityToFeedback(feedbackEntity)
    }

    @Test
    fun `Deve atualizar um feedback`(){
        val feedbackEntity = FeedbackEntity("Descricao teste Atualizado", "titulo teste Atualizado", UUID.randomUUID())
        every { entityRepository.updateCql(feedbackEntity) } returns feedbackEntity

        val response = entityService.update(feedbackEntity)

        response shouldBe FeedbackConverter.feedbackEntityToFeedback(feedbackEntity)
    }

    @Test
    fun `Deve deletar um feedback por id`(){
        val feedbackEntity = FeedbackEntity(id = UUID.randomUUID())
        every { entityRepository.deleteCql(feedbackEntity) } returns Unit

        val response = entityService.delete(feedbackEntity)
        response shouldBe Unit
    }
}