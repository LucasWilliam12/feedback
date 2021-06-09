package com.iupp.warriors.controllers.funcionario

import com.iupp.warriors.dtos.requests.FuncionarioRequest
import com.iupp.warriors.models.Funcionario
import com.iupp.warriors.services.FuncionarioService
import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Put
import io.micronaut.validation.Validated
import javax.inject.Inject
import javax.validation.Valid

@Validated
@Controller("/funcionarios")
class AtualizaFuncionarioController {

    @Inject
    lateinit var funcionarioService: FuncionarioService

    @Put("/{id}")
    fun atualizar(@PathVariable id: Long, @Body @Valid request: FuncionarioRequest): MutableHttpResponse<Funcionario> {

        val funcionario = funcionarioService.atualizar(id, request.toModel())

        return HttpResponse.ok(funcionario)
    }

}