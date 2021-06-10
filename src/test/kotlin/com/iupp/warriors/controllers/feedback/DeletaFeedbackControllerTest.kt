package com.iupp.warriors.controllers.feedback

import com.iupp.warriors.enums.TipoCargo
import com.iupp.warriors.models.Feedback
import com.iupp.warriors.models.Funcionario
import com.iupp.warriors.repositories.FeedbackRepository
import com.iupp.warriors.repositories.FuncionarioRepository
import com.iupp.warriors.utils.exceptions.ObjectNotFoundException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpStatus
import io.micronaut.test.extensions.kotest.annotation.MicronautTest

@MicronautTest
internal class DeletaFeedbackControllerTest(
    val deletaFeedbackController: DeletaFeedbackController,
    val repository: FeedbackRepository,
    val funcionarioRepository: FuncionarioRepository
): BehaviorSpec({


    given("controller responsavel por deletar um feedback"){
        // Happy path
        `when`("passando um id existente para ser deletado"){

            var avaliado = Funcionario("Avaliado teste", "avaliado@email.com", "50983833079", TipoCargo.VENDEDOR)
            var avaliador = Funcionario("Avaliador teste", "avaliador@email.com", "50983833079", TipoCargo.GERENTE)

            avaliado = funcionarioRepository.save(avaliado)
            avaliador = funcionarioRepository.save(avaliador)

            val feedbackSave = repository.save(
                Feedback("Descricao Teste", "Titulo Teste", avaliado, avaliador)
            )

            val result = deletaFeedbackController.deletar(feedbackSave.id!!)

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
                deletaFeedbackController.deletar(2)
            }
            then("o resultado e uma excecao"){
                response.message shouldBe "Feedback não encontrado com o id informado"
            }
        }
    }
})