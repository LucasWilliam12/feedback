package com.iupp.warriors.repositories

import com.iupp.warriors.models.Feedback
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

@Repository
interface FeedbackRepository: JpaRepository<Feedback, Long> {
}