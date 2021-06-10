package com.iupp.warriors.dtos.requests

import com.iupp.warriors.models.Feedback
import com.iupp.warriors.repositories.FuncionarioRepository
import com.iupp.warriors.utils.exceptions.ObjectNotFoundException
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

@Introspected
class FeedbackRequest(
    @NotNull
    @NotBlank
    val descricao: String,
    @NotNull
    @NotBlank
    val titulo: String,
    @NotNull
    @Positive
    val avaliado: Long,
    @NotNull
    @Positive
    val avaliador: Long
) {
    fun toModel(funcionarioRepository: FuncionarioRepository): Feedback {
        val avaliado = funcionarioRepository.findById(avaliado).orElseThrow{ ObjectNotFoundException("Avaliado não encontrado pelo id informado") }
        val avaliador = funcionarioRepository.findById(avaliador).orElseThrow{ ObjectNotFoundException("Avaliador não encontrado pelo id informado") }

        return Feedback(descricao, titulo, avaliado, avaliador)
    }
}
