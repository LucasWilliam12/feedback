package com.iupp.warriors.services.funcionario

import com.iupp.warriors.dtos.responses.FuncionarioResponse
import com.iupp.warriors.models.Funcionario

interface FuncionarioService {
    fun cadastrar(request: Funcionario): Funcionario
    fun consultar(id: Long): Funcionario
    fun listar(): List<FuncionarioResponse>
    fun atualizar(id: Long, request: Funcionario): Funcionario
    fun deletar(id: Long)
}