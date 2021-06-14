package com.iupp.warriors.controllers

import com.iupp.warriors.controllers.handler.HandlerException
import com.iupp.warriors.dtos.requests.FuncionarioRequest
import com.iupp.warriors.dtos.responses.FuncionarioResponse
import com.iupp.warriors.models.Funcionario
import com.iupp.warriors.services.funcionario.FuncionarioService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import javax.transaction.Transactional
import javax.validation.Valid

@HandlerException
@Validated
@Controller("/funcionarios")
class FuncionarioController(private var funcionarioService: FuncionarioService) {

    @Post
    @Transactional
    fun cadastrar(@Valid @Body request: FuncionarioRequest): HttpResponse<Any>{
        var funcionario = request.toModel()
        funcionario = funcionarioService.cadastrar(funcionario)

        val uri = UriBuilder.of("/funcionarios/{id}")
            .expand(mutableMapOf(Pair("id", funcionario.id)))

        return HttpResponse.created(uri)
    }

    @Put("/{id}")
    fun atualizar(@PathVariable id: Long, @Body @Valid request: FuncionarioRequest): HttpResponse<Funcionario> {

        val funcionario = funcionarioService.atualizar(id, request.toModel())

        return HttpResponse.ok(funcionario)
    }

    @Delete("/{id}")
    fun deletar(@PathVariable id: Long): HttpResponse<Any>{
        funcionarioService.deletar(id)
        return HttpResponse.ok()
    }

    @Get("/{id}")
    fun consultar(@PathVariable id: Long): HttpResponse<FuncionarioResponse>{
        val funcionario = funcionarioService.consultar(id)

        return HttpResponse.ok(FuncionarioResponse(funcionario))
    }

    @Get("/listar")
    fun listar(): HttpResponse<List<FuncionarioResponse>>{
        val funcionarios = funcionarioService.listar()

        return HttpResponse.ok(funcionarios)
    }
}