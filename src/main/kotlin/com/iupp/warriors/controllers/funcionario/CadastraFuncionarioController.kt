package com.iupp.warriors.controllers.funcionario

import com.iupp.warriors.dtos.requests.FuncionarioRequest
import com.iupp.warriors.services.FuncionarioService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.transaction.Transactional
import javax.validation.Valid

@Validated
@Controller("/funcionarios")
class CadastraFuncionarioController {

    val logger = LoggerFactory.getLogger(CadastraFuncionarioController::class.java)

    @Inject
    lateinit var funcionarioService: FuncionarioService

    @Post
    @Transactional
    fun cadastrar(@Valid @Body request: FuncionarioRequest): HttpResponse<Any>{

        logger.info("Salvando o funcionario:")
        var funcionario = request.toModel()
        funcionario = funcionarioService.cadastrar(funcionario)

        val uri = UriBuilder.of("/funcionarios/{id}")
            .expand(mutableMapOf(Pair("id", funcionario.id)))
        logger.info("Funcionario cadastrado.")

        return HttpResponse.created(uri)
    }

}