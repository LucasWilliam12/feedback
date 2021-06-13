package com.iupp.warriors.models

import com.iupp.warriors.enums.TipoCargo
import io.micronaut.core.annotation.Introspected
import org.hibernate.validator.constraints.br.CPF
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Introspected
@Entity
data class Funcionario(
    @field:NotNull
    @field:NotBlank
    var nome: String,
    @field:NotNull
    @field:NotBlank
    @field:Email
    var email: String,
    @field:NotNull
    @field:NotBlank
    @field:CPF
    var cpf: String,
    @field:NotNull
    var cargo: TipoCargo,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var createdAt: LocalDateTime = LocalDateTime.now()
){
    fun atualiza(request: Funcionario) {
        this.nome = request.nome
        this.email = request.email
        this.cpf = request.cpf
        this.cargo = request.cargo
    }

}
