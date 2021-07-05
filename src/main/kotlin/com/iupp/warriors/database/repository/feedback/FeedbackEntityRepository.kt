package com.iupp.warriors.database.repository.feedback

import com.iupp.warriors.database.entity.FeedbackEntity
import java.util.*
import javax.inject.Singleton

@Singleton
interface FeedbackEntityRepository {
    fun saveCql(feedbackEntity: FeedbackEntity): FeedbackEntity
    fun updateCql(feedbackEntity: FeedbackEntity): FeedbackEntity
    fun deleteCql(id: UUID)
}