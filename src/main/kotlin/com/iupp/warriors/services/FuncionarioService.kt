package com.iupp.warriors.services

import com.iupp.warriors.dtos.responses.FuncionarioResponse
import com.iupp.warriors.models.Funcionario
import com.iupp.warriors.repositories.FuncionarioRepository
import com.iupp.warriors.utils.exceptions.ObjectNotFoundException
import io.micronaut.validation.Validated
import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional
import javax.validation.Valid

@Validated
@Singleton
class FuncionarioService {

    @Inject
    lateinit var funcionarioRepository: FuncionarioRepository

    @Transactional
    fun cadastrar(@Valid request: Funcionario): Funcionario{
        return funcionarioRepository.save(request)
    }

    @Transactional
    fun consultar(id: Long): Funcionario {
        return funcionarioRepository.findById(id)
            .orElseThrow { ObjectNotFoundException("Usuario não encontrado") }
    }

    @Transactional
    fun listar(): List<FuncionarioResponse> {
        return funcionarioRepository.findAll().map {
            FuncionarioResponse(it)
        }
    }

    @Transactional
    fun deletar(id: Long) {
        val funcionario = this.consultar(id)
        funcionarioRepository.delete(funcionario)
    }

    @Transactional
    fun atualizar(id: Long, request: Funcionario): Funcionario {
        val funcionario = this.consultar(id)
        funcionario.atualiza(request)
        return funcionarioRepository.update(funcionario)
    }

}