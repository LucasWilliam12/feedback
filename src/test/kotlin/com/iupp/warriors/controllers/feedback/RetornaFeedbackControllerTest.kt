package com.iupp.warriors.controllers.feedback

import com.iupp.warriors.enums.TipoCargo
import com.iupp.warriors.models.Feedback
import com.iupp.warriors.models.Funcionario
import com.iupp.warriors.repositories.FeedbackRepository
import com.iupp.warriors.repositories.FuncionarioRepository
import com.iupp.warriors.utils.exceptions.ObjectNotFoundException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpStatus
import io.micronaut.test.annotation.TransactionMode
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*

@MicronautTest(transactionMode = TransactionMode.SEPARATE_TRANSACTIONS)
internal class RetornaFeedbackControllerTest(
    val retornaFeedbackController: RetornaFeedbackController,
    val repository: FeedbackRepository,
    val funcionarioRepository: FuncionarioRepository
): BehaviorSpec({

    var avaliado = Funcionario("Avaliado teste", "avaliado@email.com", "50983833079", TipoCargo.VENDEDOR)
    var avaliador = Funcionario("Avaliador teste", "avaliador@email.com", "50983833079", TipoCargo.GERENTE)

    given("controller responsavel por retornar registros de feedbacks"){
        // Happy path
        `when`("passando um id existente para um funcionario ser retornado"){

            avaliado = funcionarioRepository.save(avaliado)
            avaliador = funcionarioRepository.save(avaliador)

            val feedbackSave = repository.save(
                Feedback("Descricao Teste", "Titulo Teste", avaliado, avaliador)
            )

            val result = retornaFeedbackController.consultar(feedbackSave.id!!)

            then("o resultado e um status 200 ok"){
                with(result){
                    status shouldBe HttpStatus.OK
                    body.isPresent shouldBe true
                }
            }
        }

        // Exceção ao consultar
        `when`("passando um id inexistente para um feedback ser retornado"){
            val response = shouldThrow<ObjectNotFoundException> {
                retornaFeedbackController.consultar(2)
            }
            then("o resultado e uma excecao"){
                response.message shouldBe "Feedback não encontrado com o id informado"
            }
        }

        // Happy path
        `when`("deve retornar uma lista de funcionarios"){

            repository.saveAll(
                mutableListOf(
                    Feedback("Descricao Teste", "Titulo Teste", avaliado, avaliador),
                    Feedback("Descricao Teste 2", "Titulo Teste 2", avaliado, avaliador)
                ))

            val result = retornaFeedbackController.listar()

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