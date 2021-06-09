package com.iupp.warriors.controllers.funcionario

import com.iupp.warriors.services.FuncionarioService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.PathVariable
import org.slf4j.LoggerFactory
import javax.inject.Inject

@Controller("/funcionarios")
class DeletaFuncionarioController {

    @Inject
    lateinit var funcionarioService: FuncionarioService

    @Delete("/{id}")
    fun deletar(@PathVariable id: Long): HttpResponse<Any>{
        funcionarioService.deletar(id)
        return HttpResponse.ok()
    }

}