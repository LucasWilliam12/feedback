package com.iupp.warriors.controllers.funcionario

import com.iupp.warriors.dtos.responses.FuncionarioResponse
import com.iupp.warriors.repositories.FuncionarioRepository
import com.iupp.warriors.utils.exceptions.ObjectNotFoundException
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import javax.inject.Inject

@Validated
@Controller("/funcionarios")
class RetornaFuncionarioPorIdController {
    val logger = LoggerFactory.getLogger(RetornaFuncionarioPorIdController::class.java)

    @Inject
    lateinit var funcionarioRepository: FuncionarioRepository

    @Get("/{id}")
    fun consultar(@PathVariable id: Long): HttpResponse<Any>{
        logger.info("Consultando o banco")
        val funcionario = funcionarioRepository.findById(id)
            .orElseThrow {ObjectNotFoundException("Usuario não encontrado")}

        logger.info("Retornando o usuario encontrado")
        val funcionarioResponse = FuncionarioResponse(funcionario)
        return HttpResponse.ok(funcionarioResponse)
    }

}