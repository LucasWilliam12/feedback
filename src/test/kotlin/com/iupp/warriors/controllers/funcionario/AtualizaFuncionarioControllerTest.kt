package com.iupp.warriors.controllers.funcionario

import com.iupp.warriors.dtos.requests.FuncionarioRequest
import com.iupp.warriors.enums.TipoCargo
import com.iupp.warriors.models.Funcionario
import com.iupp.warriors.repositories.FuncionarioRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpStatus
import io.micronaut.test.annotation.TransactionMode
import io.micronaut.test.extensions.kotest.annotation.MicronautTest

@MicronautTest(transactionMode = TransactionMode.SEPARATE_TRANSACTIONS)
internal class AtualizaFuncionarioControllerTest(
    val atualizaFuncionarioController: AtualizaFuncionarioController,
    val repository: FuncionarioRepository
): BehaviorSpec({

    // Happy path
    given("controller responsavel por editar um funcionario"){
        `when`("passando um usuario com os dados validos para ser editado"){

            val funcionarioSave = repository.save(Funcionario("Usuario Teste",
                "usuarioteste@hotmail.com",
                "31365056031",
                TipoCargo.GERENTE))

            val result = atualizaFuncionarioController.atualizar(funcionarioSave.id!!,
                FuncionarioRequest(
                    "Usuario Teste Editado",
                    "usuarioteste@hotmail.com",
                    "31365056031",
                    TipoCargo.VENDEDOR
                )
            )
            then("o resultado e um status 200 ok"){
                with(result){
                    status shouldBe HttpStatus.OK
                    body.get().nome shouldBe "Usuario Teste Editado"
                    body.get().email shouldBe "usuarioteste@hotmail.com"
                    body.get().cargo shouldBe TipoCargo.VENDEDOR
                }
            }
        }
    }
})