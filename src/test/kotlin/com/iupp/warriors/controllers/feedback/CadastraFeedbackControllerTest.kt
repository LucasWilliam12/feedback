package com.iupp.warriors.controllers.feedback

import com.iupp.warriors.dtos.requests.FeedbackRequest
import com.iupp.warriors.enums.TipoCargo
import com.iupp.warriors.models.Funcionario
import com.iupp.warriors.repositories.FuncionarioRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpStatus
import io.micronaut.test.extensions.kotest.annotation.MicronautTest

@MicronautTest
internal class CadastraFeedbackControllerTest(
    val cadastraFeedbackController: CadastraFeedbackController,
    val funcionarioRepository: FuncionarioRepository
): BehaviorSpec({

    // Happy path
    given("controller responsavel por cadastrar um feedback"){
        `when`("passando um feedback com os dados validos para ser cadastrado"){

            val avaliado = Funcionario("Avaliado teste", "avaliado@email.com", "50983833079", TipoCargo.VENDEDOR)
            val avaliador = Funcionario("Avaliador teste", "avaliador@email.com", "50983833079", TipoCargo.GERENTE)

            funcionarioRepository.save(avaliado)
            funcionarioRepository.save(avaliador)

            val result = cadastraFeedbackController.cadastrar(
                FeedbackRequest("Descricao Teste", "Titulo Teste", 1, 2)
            )
            then("o resultado e um status 201 created"){
                result.status shouldBe HttpStatus.CREATED
            }
        }
    }
})