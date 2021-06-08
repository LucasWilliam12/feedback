package com.iupp.warriors.models

import com.iupp.warriors.enums.TipoCargo
import io.micronaut.core.annotation.Introspected
import org.hibernate.validator.constraints.br.CPF
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Introspected
@Entity
data class Funcionario(
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    val createdAt = LocalDateTime.now()

}
