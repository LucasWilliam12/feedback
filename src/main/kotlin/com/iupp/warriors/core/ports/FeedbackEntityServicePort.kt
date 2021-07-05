package com.iupp.warriors.core.ports

import com.iupp.warriors.core.model.Feedback
import com.iupp.warriors.database.entity.FeedbackEntity
import java.util.*
import javax.inject.Singleton

@Singleton
interface FeedbackEntityServicePort {
    fun save(feedbackEntity: FeedbackEntity): Feedback
    fun update(feedbackEntity: FeedbackEntity): Feedback
    fun delete(id: UUID)
}