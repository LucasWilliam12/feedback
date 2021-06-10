package com.iupp.warriors.services

import com.iupp.warriors.enums.TipoCargo
import com.iupp.warriors.models.Feedback
import com.iupp.warriors.models.Funcionario
import com.iupp.warriors.repositories.FeedbackRepository
import com.iupp.warriors.services.feedback.FeedbackServiceImpl
import com.iupp.warriors.utils.exceptions.ActionNotPermited
import com.iupp.warriors.utils.exceptions.ObjectNotFoundException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.kotest.MicronautKotestExtension.getMock
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.time.LocalDateTime
import java.util.*

@MicronautTest
internal class FeedbackServiceImplTest(
    val feedbackRepository: FeedbackRepository,
): BehaviorSpec({
    given("metodos services responsaveis por cadastrar, retornar e alterar feedbacks") {
        val avaliador = Funcionario(
            "Avaliador Teste",
            "avaliadorteste@hotmail.com",
            "31365056031",
            TipoCargo.GERENTE
        )

        val avaliado = Funcionario(
            "Avaliado Teste",
            "avaliadoteste@hotmail.com",
            "31365056031",
            TipoCargo.VENDEDOR
        )

        val feedback = Feedback("Descricao teste", "Titulo teste", avaliado, avaliador)

        val repositoryMock = getMock(feedbackRepository)
        val feedbackService = FeedbackServiceImpl(repositoryMock)

        every { repositoryMock.save(feedback) } answers {
            feedback.id = 1
            feedback.createdAt = LocalDateTime.now()
            feedback
        }

        `when`("passando um feedback com os dados validos para ser cadastrado") {
            then("retorna um feedback com id") {
                val response = feedbackService.cadastrar(feedback)
                response.id shouldNotBe null
                response.titulo shouldBe feedback.titulo
                response.descricao shouldBe feedback.descricao
                response.avaliador.id shouldBe feedback.avaliador.id
                response.avaliado.id shouldBe feedback.avaliado.id
                verify { repositoryMock.save(feedback) }
            }
        }

        feedback.avaliador = avaliado
        feedback.avaliado = avaliador

        `when`("passando um feedback cujo o avaliador tenha o nivel de vendedor para ser cadastrado") {
            val response = shouldThrow<ActionNotPermited> { feedbackService.cadastrar(feedback) }
            then("retorna uma excecao") {
                response.message shouldBe  "Funcionario do tipo vendedor não pode cadastrar feedbacks"
                verify { repositoryMock.save(feedback) }
            }
        }

        val feedbackAtualizado = Feedback("Descricao teste 2", "Titulo teste 2", avaliado, avaliador)
        feedback.avaliador = avaliador
        feedback.avaliado = avaliado

        every { repositoryMock.findById(1) } answers {
            feedback.id = 1
            Optional.of(feedback)
        }

        every { repositoryMock.update(feedback) } answers {
            feedback.id = 1
            feedback.titulo = feedbackAtualizado.titulo
            feedback.descricao = feedbackAtualizado.descricao
            feedback
        }

        `when`("passando um feedback com os dados validos para ser atualizado") {
            then("retorna um feedback com os valores alterados") {
                val response = feedbackService.atualizar(feedback.id!!, feedback)
                response.id shouldNotBe null
                response.titulo shouldBe feedbackAtualizado.titulo
                response.descricao shouldBe feedbackAtualizado.descricao
                response.avaliador.id shouldBe feedback.avaliador.id
                response.avaliado.id shouldBe feedback.avaliado.id
                verify { repositoryMock.update(feedback) }
            }
        }

        every { repositoryMock.findById(2) } answers {
            Optional.empty()
        }

        `when`("passando um id de feedback inexistente para ser atualizado") {
            val response = shouldThrow<ObjectNotFoundException> {
                feedbackService.atualizar(2, feedback)
            }
            then("retorna uma excecao ObjectNotFoundException") {
                response.message shouldBe "Feedback não encontrado com o id informado"
                verify { repositoryMock.findById(2) }
            }
        }

        `when`("passando um id de feedback existente para ser retornado") {
            then("retorna um feedback") {
                val response = feedbackService.consultar(1)
                response.id shouldBe 1
                response.titulo shouldBe feedback.titulo
                verify { repositoryMock.findById(1) }
            }
        }
//
        `when`("passando um id de feedback inexistente para ser retornado") {
            val response = shouldThrow<ObjectNotFoundException> {
                feedbackService.consultar(2)
            }
            then("retorna uma excecao ObjectNotFoundException") {
                response.message shouldBe "Feedback não encontrado com o id informado"
                verify { repositoryMock.findById(2) }
            }
        }

        every { repositoryMock.findAll() } answers {
            listOf(feedback, feedbackAtualizado)
        }

        `when`("chamando servico que retorna uma lista de feedbacks") {
            then("retorna uma lista de feedbacks") {

                val response = feedbackService.listar()

                response.size shouldBe 2
                verify { repositoryMock.findAll() }
            }
        }
    }
}){

    @MockBean(FeedbackRepository::class)
    fun feedbackRepository(): FeedbackRepository {
        return mockk()
    }
}