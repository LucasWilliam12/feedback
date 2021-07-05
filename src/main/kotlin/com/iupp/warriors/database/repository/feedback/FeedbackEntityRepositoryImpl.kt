package com.iupp.warriors.database.repository.feedback

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.cql.SimpleStatement
import com.iupp.warriors.database.entity.FeedbackEntity
import org.slf4j.LoggerFactory
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Singleton

@Singleton
class FeedbackEntityRepositoryImpl(private val cqlSession: CqlSession): FeedbackEntityRepository {

    val logger = LoggerFactory.getLogger(FeedbackEntityRepositoryImpl::class.java)

    val uuid = UUID.randomUUID()

    override fun saveCql(feedbackEntity: FeedbackEntity): FeedbackEntity {
        logger.info("Salvando um feedback no banco de dados Scylla.")
        cqlSession.execute(
            SimpleStatement.newInstance("INSERT INTO feedbackkeyspace.Feedbacks(id, descricao, titulo, createdAt) VALUES (?,?,?,?)",
                uuid,
                feedbackEntity.descricao,
                feedbackEntity.titulo,
                feedbackEntity.createdAt!!.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
        )
        feedbackEntity.id = uuid
        return feedbackEntity
    }

    override fun updateCql(feedbackEntity: FeedbackEntity): FeedbackEntity {
        logger.info("Atualizando feedback no banco de dados Scylla: $feedbackEntity")
        cqlSession.execute(
            SimpleStatement.newInstance("UPDATE feedbackkeyspace.Feedbacks SET descricao = ?, titulo = ? WHERE id = ? IF EXISTS",
                feedbackEntity.descricao,
                feedbackEntity.titulo,
                feedbackEntity.id
            )
        )

        return feedbackEntity
    }

    override fun deleteCql(id: UUID) {
        logger.info("Deletando feedback no banco de dados Scylla: $id")
        cqlSession.execute(
            SimpleStatement.newInstance("DELETE FROM feedbackkeyspace.Feedbacks WHERE id = ? IF EXISTS",
                id
            )
        )
    }
}