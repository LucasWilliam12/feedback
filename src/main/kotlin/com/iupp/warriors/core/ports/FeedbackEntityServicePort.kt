package com.iupp.warriors.core.ports

import com.iupp.warriors.core.models.Feedback
import com.iupp.warriors.database.entity.FeedbackEntity
import javax.inject.Singleton

@Singleton
interface FeedbackEntityServicePort {
    fun save(feedbackEntity: FeedbackEntity): Feedback
}