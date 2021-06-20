package com.iupp.warriors.database.repository.feedback

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.cql.SimpleStatement
import com.iupp.warriors.database.entity.FeedbackEntity
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Singleton

@Singleton
class FeedbackEntityRepositoryImpl(private val cqlSession: CqlSession): FeedbackEntityRepository {

    val uuid = UUID.randomUUID()

    override fun saveCql(feedbackEntity: FeedbackEntity): FeedbackEntity {
        val format = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        println(feedbackEntity.createdAt!!.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
        cqlSession.execute(
            SimpleStatement.newInstance("INSERT INTO feedbackkeyspace.Feedback(id, descricao, titulo, id_avaliado, id_avaliador, createdAt) VALUES (?,?,?,?,?,?)",
                uuid,
                feedbackEntity.descricao,
                feedbackEntity.titulo,
                feedbackEntity.avaliado,
                feedbackEntity.avaliador,
                feedbackEntity.createdAt!!.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
        )
        feedbackEntity.id = uuid
        return feedbackEntity
    }
}