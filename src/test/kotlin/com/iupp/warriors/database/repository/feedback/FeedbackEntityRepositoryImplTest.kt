package com.iupp.warriors.database.repository.feedback

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.cql.SimpleStatement
import com.iupp.warriors.database.entity.FeedbackEntity
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.extension.ExtendWith
import java.time.ZoneId
import java.util.*

@MicronautTest
@ExtendWith(MockKExtension::class)
internal class FeedbackEntityRepositoryImplTest: AnnotationSpec(){

    @InjectMockKs
    lateinit var entityRepository: FeedbackEntityRepositoryImpl
    @RelaxedMockK
    lateinit var cqlSession: CqlSession

    @BeforeEach
    fun setUp(){
        MockKAnnotations.init(this)
    }

    @Test
    fun `Deve cadastrar um novo feedback`(){
        val feedbackEntity = FeedbackEntity("Descricao teste", "Titulo teste")
        cqlSession.execute(
            SimpleStatement
                .newInstance(
                    "INSERT INTO feedbackkeyspace.Feedbacks(id, descricao, titulo, createdAt) VALUES (?,?,?,?)",
                    UUID.randomUUID(),
                    feedbackEntity.descricao,
                    feedbackEntity.titulo,
                    feedbackEntity.createdAt!!.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
            )

        val response = entityRepository.saveCql(feedbackEntity)
        response shouldBe feedbackEntity
    }

    @Test
    fun `Deve atualizar um feedback`(){
        val feedbackEntity = FeedbackEntity("Descricao teste Atualizada", "Titulo teste Atualizada", UUID.randomUUID())
        cqlSession.execute(
            SimpleStatement.newInstance("UPDATE feedbackkeyspace.Feedbacks SET descricao = ?, titulo = ? WHERE id = ? IF EXISTS",
                feedbackEntity.descricao,
                feedbackEntity.titulo,
                feedbackEntity.id
            )
        )
        val response = entityRepository.updateCql(feedbackEntity)
        response shouldBe feedbackEntity
    }

    @Test
    fun `Deve deletar um feedback`(){
        val feedbackEntity = FeedbackEntity(id = UUID.randomUUID())
        cqlSession.execute(
            SimpleStatement.newInstance("DELETE FROM feedbackkeyspace.Feedbacks WHERE id = ? IF EXISTS",
                feedbackEntity.id
            )
        )
        val response = entityRepository.deleteCql(feedbackEntity)
        response shouldBe Unit
    }

}
