package com.iupp.warriors.services.funcionario

import com.iupp.warriors.dtos.responses.FuncionarioResponse
import com.iupp.warriors.models.Funcionario
import com.iupp.warriors.repositories.FuncionarioRepository
import com.iupp.warriors.controllers.handler.exceptions.ObjectNotFoundException
import io.micronaut.validation.Validated
import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional
import javax.validation.Valid

@Validated
@Singleton
class FuncionarioServiceImpl(@Inject val funcionarioRepository: FuncionarioRepository): FuncionarioService {

    @Transactional
    override fun cadastrar(@Valid request: Funcionario): Funcionario{
        return funcionarioRepository.save(request)
    }

    @Transactional
    override fun consultar(id: Long): Funcionario {
        return funcionarioRepository.findById(id)
            .orElseThrow { ObjectNotFoundException("Usuario não encontrado") }
    }

    @Transactional
    override fun listar(): List<FuncionarioResponse> {
        return funcionarioRepository.findAll().map {
            FuncionarioResponse(it)
        }
    }

    @Transactional
    override fun deletar(id: Long) {
        val funcionario = this.consultar(id)
        funcionarioRepository.delete(funcionario)
    }

    @Transactional
    override fun atualizar(id: Long, request: Funcionario): Funcionario {
        val funcionario = this.consultar(id)
        funcionario.atualiza(request)
        return funcionarioRepository.update(funcionario)
    }

}