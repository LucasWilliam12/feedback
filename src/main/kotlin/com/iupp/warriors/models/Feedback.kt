package com.iupp.warriors.models

import io.micronaut.core.annotation.Introspected
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.Valid
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Entity
@Introspected
data class Feedback(
    @field:NotNull
    @field:NotEmpty
    var descricao: String,
    @field:NotNull
    @field:NotEmpty
    var titulo: String,
    @field:NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "avaliado_id")
    var avaliado: Funcionario,
    @field:NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "avaliador_id")
    var avaliador: Funcionario,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var createdAt: LocalDateTime = LocalDateTime.now()
) {

    fun atualiza(@Valid request: Feedback) {
        this.titulo = request.titulo
        this.descricao = request.descricao
        this.avaliado = request.avaliado
        this.avaliador = request.avaliador
    }

}
