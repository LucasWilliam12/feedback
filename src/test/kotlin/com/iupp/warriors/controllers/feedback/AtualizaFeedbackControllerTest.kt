package com.iupp.warriors.controllers.feedback

import com.iupp.warriors.dtos.requests.FeedbackRequest
import com.iupp.warriors.enums.TipoCargo
import com.iupp.warriors.models.Feedback
import com.iupp.warriors.models.Funcionario
import com.iupp.warriors.repositories.FeedbackRepository
import com.iupp.warriors.repositories.FuncionarioRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.kotest.annotation.MicronautTest

@MicronautTest
internal class AtualizaFeedbackControllerTest(
    val repository: FeedbackRepository,
    val atualizaFeedbackController: AtualizaFeedbackController,
    val funcionarioRepository: FuncionarioRepository
): BehaviorSpec({

    // Happy path
    given("controller responsavel por editar um feedback"){
        `when`("passando um feedback com os dados validos para ser editado"){

            var avaliado = Funcionario("Avaliado teste", "avaliado@email.com", "50983833079", TipoCargo.VENDEDOR)
            var avaliador = Funcionario("Avaliador teste", "avaliador@email.com", "50983833079", TipoCargo.GERENTE)

            avaliado = funcionarioRepository.save(avaliado)
            avaliador = funcionarioRepository.save(avaliador)

            val feedbackSave = repository.save(
                Feedback("Descricao Teste", "Titulo Teste", avaliado, avaliador)
            )

            val result = atualizaFeedbackController.atualizar(feedbackSave.id!!,
                FeedbackRequest("Descricao teste 2", "titulo teste 2", 1, 2)
            )

            then("o resultado e um status 200 ok"){
                with(result){
                    status shouldBe io.micronaut.http.HttpStatus.OK
                    body.get().avaliado.cargo shouldBe avaliado.cargo
                    body.get().avaliador.cargo shouldBe avaliador.cargo
                }
            }
        }
    }
})