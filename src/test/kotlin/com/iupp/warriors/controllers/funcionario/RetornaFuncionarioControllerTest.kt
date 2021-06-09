package com.iupp.warriors.controllers.funcionario

import com.iupp.warriors.enums.TipoCargo
import com.iupp.warriors.models.Funcionario
import com.iupp.warriors.repositories.FuncionarioRepository
import com.iupp.warriors.utils.exceptions.ObjectNotFoundException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpStatus
import io.micronaut.test.extensions.kotest.annotation.MicronautTest

@MicronautTest
internal class RetornaFuncionarioControllerTest(
    val retornaFuncionarioController: RetornaFuncionarioController,
    val repository: FuncionarioRepository
): BehaviorSpec({

    given("controller responsavel por retornar registros de funcionarios"){
        // Happy path
        `when`("passando um id existente para um funcionario ser retornado"){

            val funcionarioNovo = repository.save(
                Funcionario("Usuario Teste",
                    "usuarioteste@hotmail.com",
                    "31365056031",
                    TipoCargo.GERENTE)
            )

            val result = retornaFuncionarioController.consultar(funcionarioNovo.id!!)

            then("o resultado e um status 200 ok"){
                with(result){
                    status shouldBe HttpStatus.OK
                    body.isPresent shouldBe true
                }
            }
        }

        // Exceção ao consultar
        `when`("passando um id inexistente para um funcionario ser retornado"){
            val response = shouldThrow<ObjectNotFoundException> {
                retornaFuncionarioController.consultar(2)
            }
            then("o resultado e uma excecao"){
                response.message shouldBe "Usuario não encontrado"
            }
        }

        // Happy path
        `when`("deve retornar uma lista de funcionarios"){

            repository.saveAll(
                mutableListOf(Funcionario("Usuario Teste",
                    "usuarioteste@hotmail.com",
                    "31365056031",
                    TipoCargo.GERENTE),
                    Funcionario("Usuario Teste2",
                        "usuarioteste2@hotmail.com",
                        "31365056031",
                        TipoCargo.VENDEDOR))
            )

            val result = retornaFuncionarioController.listar()

            then("o resultado e um status 200 ok"){
                with(result){
                    status shouldBe HttpStatus.OK
                    body.isPresent shouldBe true
                    body.get().size shouldBe 2
                }
            }
        }
    }
}){
    override fun afterEach(testCase: TestCase, result: TestResult) {
        super.afterEach(testCase, result)
        repository.deleteAll()
    }
}