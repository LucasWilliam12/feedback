package com.iupp.warriors.core.ports

import com.iupp.warriors.core.models.Feedback
import com.iupp.warriors.infrastracture.models.FeedbackEvent
import javax.inject.Singleton

@Singleton
interface FeedbackServicePort {
    fun insert(feedback: Feedback): FeedbackEvent
}