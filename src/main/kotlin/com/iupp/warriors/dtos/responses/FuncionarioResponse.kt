package com.iupp.warriors.dtos.responses

import com.iupp.warriors.enums.TipoCargo
import com.iupp.warriors.models.Funcionario

data class FuncionarioResponse(
    val nome: String,
    val email: String,
    val cargo: TipoCargo
){
    constructor(funcionario: Funcionario) : this(
        nome = funcionario.nome,
        email = funcionario.email,
        cargo = funcionario.cargo
    )
}