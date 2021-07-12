package com.iupp.warriors.core.ports

import com.iupp.warriors.core.model.Feedback
import com.iupp.warriors.infrastructure.model.feedback.FeedbackEvent
import java.util.*
import javax.inject.Singleton

@Singleton
interface FeedbackServicePort {
    fun insert(feedback: Feedback): FeedbackEvent
    fun update(feedback: Feedback): FeedbackEvent
    fun delete(id: UUID)
}