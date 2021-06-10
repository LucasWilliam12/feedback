package com.iupp.warriors.controllers.funcionario

import com.iupp.warriors.enums.TipoCargo
import com.iupp.warriors.models.Funcionario
import com.iupp.warriors.repositories.FuncionarioRepository
import com.iupp.warriors.utils.exceptions.ObjectNotFoundException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpStatus
import io.micronaut.test.annotation.TransactionMode
import io.micronaut.test.extensions.kotest.annotation.MicronautTest

@MicronautTest(transactionMode = TransactionMode.SEPARATE_TRANSACTIONS)
internal class DeletaFuncionarioControllerTest(
    val deletaFuncionarioController: DeletaFuncionarioController,
    val repository: FuncionarioRepository
): BehaviorSpec({


    given("controller responsavel por deletar um funcionario"){
        // Happy path
        `when`("passando um id existente para ser deletado"){

            val funcionarioSave = repository.save(
                Funcionario("Usuario Teste",
                "usuarioteste@hotmail.com",
                "31365056031",
                TipoCargo.GERENTE)
            )

            val result = deletaFuncionarioController.deletar(funcionarioSave.id!!)

            then("o resultado e um status 200 ok"){
                with(result){
                    status shouldBe HttpStatus.OK
                    body.isEmpty shouldBe true
                }
            }
        }

        // Exceção ao deletar
        `when`("passando um id inexistente para ser deletado"){
            val response = shouldThrow<ObjectNotFoundException> {
                deletaFuncionarioController.deletar(2)
            }
            then("o resultado e uma excecao"){
                response.message shouldBe "Usuario não encontrado"
            }
        }
    }
})