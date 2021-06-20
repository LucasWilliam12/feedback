package com.iupp.warriors.services

import com.iupp.warriors.enums.TipoCargo
import com.iupp.warriors.models.Funcionario
import com.iupp.warriors.controllers.handler.exceptions.ActionNotPermited
import com.iupp.warriors.controllers.handler.exceptions.ObjectNotFoundException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.time.LocalDateTime
import java.util.*

@MicronautTest
internal class FeedbackServiceImplTest: AnnotationSpec(){

    val feedbackRepository = mockk<FeedbackRepository>()
    val feedbackService = FeedbackServiceImpl(feedbackRepository)

    companion object{
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

    }

    @Test
    fun `deve cadastrar um feedback quando os dados informados forem validos`(){
        every { feedbackRepository.save(feedback) } answers {
            feedback.id = 1
            feedback.createdAt = LocalDateTime.now()
            feedback
        }

        val response = feedbackService.cadastrar(feedback)

        with(response){
            id shouldNotBe null
            titulo shouldBe feedback.titulo
            descricao shouldBe feedback.descricao
            avaliador.id shouldBe feedback.avaliador.id
            avaliado.id shouldBe feedback.avaliado.id
            verify { feedbackRepository.save(feedback) }
        }
    }

    @Test
    fun `passando um feedback cujo o avaliador tenha o nivel de vendedor para ser cadastrado`() {
        val feedbackInvalido = Feedback("Descricao teste", "Titulo teste", avaliador, avaliado)

        val response = shouldThrow<ActionNotPermited> { feedbackService.cadastrar(feedbackInvalido) }

        with(response){
            message shouldBe  "Funcionario do tipo vendedor não pode cadastrar feedbacks"
        }
    }

    @Test
    fun `passando um feedback com os dados validos para ser atualizado`() {
        val feedbackAtualizado = Feedback("Descricao teste 2", "Titulo teste 2", avaliado, avaliador)
        every { feedbackRepository.findById(1) } answers {
            feedback.id = 1
            Optional.of(feedback)
        }

        every { feedbackRepository.update(feedback) } answers {
            feedback.id = 1
            feedback.titulo = feedbackAtualizado.titulo
            feedback.descricao = feedbackAtualizado.descricao
            feedback
        }

        val response = feedbackService.atualizar(feedback.id!!, feedback)

        with(response) {
            id shouldNotBe null
            titulo shouldBe feedbackAtualizado.titulo
            descricao shouldBe feedbackAtualizado.descricao
            avaliador.id shouldBe feedback.avaliador.id
            avaliado.id shouldBe feedback.avaliado.id
            verify { feedbackRepository.update(feedback) }
            verify { feedbackRepository.findById(1) }
        }
    }

    @Test
    fun `passando um id de feedback inexistente para ser atualizado`() {
        every { feedbackRepository.findById(2) } answers {
            Optional.empty()
        }

        val response = shouldThrow<ObjectNotFoundException> {
            feedbackService.atualizar(2, feedback)
        }
        with(response) {
            response.message shouldBe "Feedback não encontrado com o id informado"
            verify { feedbackRepository.findById(2) }
        }
    }

    @Test
    fun `passando um id de feedback existente para ser retornado`() {
        every { feedbackRepository.findById(1) } answers {
            feedback.id = 1
            Optional.of(feedback)
        }

        val response = feedbackService.consultar(1)

        with(response) {
            response.id shouldBe 1
            response.titulo shouldBe feedback.titulo
            verify { feedbackRepository.findById(1) }
        }
    }

    @Test
    fun `passando um id de feedback inexistente para ser retornado`() {
        every { feedbackRepository.findById(2) } answers {
            Optional.empty()
        }

        val response = shouldThrow<ObjectNotFoundException> {
            feedbackService.consultar(2)
        }
        with(response) {
            response.message shouldBe "Feedback não encontrado com o id informado"
            verify { feedbackRepository.findById(2) }
        }
    }

    @Test
    fun `chamando servico que retorna uma lista de feedbacks`() {
        every { feedbackRepository.findAll() } answers {
            listOf(feedback, feedback)
        }

        val response = feedbackService.listar()

        with(response) {
            response.size shouldBe 2
            verify { feedbackRepository.findAll() }
        }
    }

    @Test
    fun `passando um id de feedback existente para ser deletado`() {
        every { feedbackRepository.findById(1) } answers {
            feedback.id = 1
            Optional.of(feedback)
        }

        every { feedbackRepository.delete(feedback) } answers {
            Unit
        }

        val response = feedbackService.deletar(1)

        response shouldBe Unit
        verify { feedbackRepository.findById(1) }
    }

    @Test
    fun `passando um id de feedback inexistente para ser deletado`() {
        every { feedbackRepository.findById(2) } answers {
            Optional.empty()
        }

        val response = shouldThrow<ObjectNotFoundException> {
            feedbackService.deletar(2)
        }
        with(response) {
            response.message shouldBe "Feedback não encontrado com o id informado"
            verify { feedbackRepository.findById(2) }
        }
    }
}