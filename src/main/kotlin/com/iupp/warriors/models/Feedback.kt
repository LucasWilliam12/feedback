package com.iupp.warriors.models

import io.micronaut.core.annotation.Introspected
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Entity
@Introspected
class Feedback(
    @NotNull
    @NotEmpty
    var descricao: String,
    @NotNull
    @NotEmpty
    var titulo: String,
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "avaliado_id")
    var avaliado: Funcionario,
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "avaliador_id")
    var avaliador: Funcionario
) {

    fun atualiza(request: Feedback) {
        this.titulo = request.titulo
        this.descricao = request.descricao
        this.avaliado = request.avaliado
        this.avaliador = request.avaliador
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var createdAt = LocalDateTime.now()

}
