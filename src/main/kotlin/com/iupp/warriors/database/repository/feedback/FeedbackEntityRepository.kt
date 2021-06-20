package com.iupp.warriors.database.repository.feedback

import com.iupp.warriors.database.entity.FeedbackEntity
import javax.inject.Singleton

@Singleton
interface FeedbackEntityRepository {
    fun saveCql(feedbackEntity: FeedbackEntity): FeedbackEntity
}