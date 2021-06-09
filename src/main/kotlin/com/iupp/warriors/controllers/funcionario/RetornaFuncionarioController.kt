package com.iupp.warriors.controllers.funcionario

import com.iupp.warriors.dtos.responses.FuncionarioResponse
import com.iupp.warriors.services.FuncionarioService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import javax.inject.Inject

@Validated
@Controller("/funcionarios")
class RetornaFuncionarioController {
    val logger = LoggerFactory.getLogger(RetornaFuncionarioController::class.java)

    @Inject
    lateinit var funcionarioService: FuncionarioService

    @Get("/{id}")
    fun consultar(@PathVariable id: Long): HttpResponse<Any>{
        logger.info("Consultando o banco")
        val funcionario = funcionarioService.consultar(id)

        logger.info("Retornando o usuario encontrado")
        return HttpResponse.ok(FuncionarioResponse(funcionario))
    }

    @Get("/listar")
    fun listar(): HttpResponse<List<FuncionarioResponse>>{
        logger.info("Consultando o banco")
        val funcionarios = funcionarioService.listar()

        logger.info("Listando os usuarios encontrados")
        return HttpResponse.ok(funcionarios)
    }

}