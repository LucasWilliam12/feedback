package com.iupp.warriors.controllers.funcionario

import com.iupp.warriors.dtos.requests.FuncionarioRequest
import com.iupp.warriors.enums.TipoCargo
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpStatus
import io.micronaut.test.annotation.TransactionMode
import io.micronaut.test.extensions.kotest.annotation.MicronautTest

@MicronautTest(transactionMode = TransactionMode.SEPARATE_TRANSACTIONS)
class CadastraFuncionarioControllerTest(val cadastraFuncionarioController: CadastraFuncionarioController): BehaviorSpec({

    // Happy path
    given("controller responsavel por cadastrar um funcionario"){
        `when`("passando um funcionario com os dados validos para ser cadastrado"){
            val result = cadastraFuncionarioController.cadastrar(FuncionarioRequest(
                "Usuario Teste",
                "usuarioteste@hotmail.com",
                "31365056031",
                TipoCargo.GERENTE
            ))
            then("o resultado e um status 201 created"){
                result.status shouldBe HttpStatus.CREATED
            }
        }
    }
})