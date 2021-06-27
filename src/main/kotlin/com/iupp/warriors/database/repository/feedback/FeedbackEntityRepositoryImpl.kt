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
}