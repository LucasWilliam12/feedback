package com.iupp.warriors.dtos.requests

import com.iupp.warriors.enums.TipoCargo
import com.iupp.warriors.models.Funcionario
import io.micronaut.core.annotation.Introspected
import org.hibernate.validator.constraints.br.CPF
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Introspected
class FuncionarioRequest(
    @field:NotNull
    @field:NotBlank
    val nome: String,
    @field:NotNull
    @field:NotBlank
    @field:Email
    val email: String,
    @field:NotNull
    @field:NotBlank
    @field:CPF
    val cpf: String,
    @field:NotNull
    val cargo: TipoCargo
){
    fun toModel(): Funcionario {
        return Funcionario(this.nome, this.email, this.cpf, this.cargo)
    }
}
