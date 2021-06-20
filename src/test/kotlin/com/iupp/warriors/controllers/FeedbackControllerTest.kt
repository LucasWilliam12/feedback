package com.iupp.warriors.controllers

import com.iupp.warriors.dtos.requests.FeedbackRequest
import com.iupp.warriors.dtos.responses.FeedbackResponse
import com.iupp.warriors.enums.TipoCargo
import com.iupp.warriors.models.Funcionario
import io.kotest.core.spec.style.AnnotationSpec
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import java.util.*

@MicronautTest
class FeedbackControllerTest(): AnnotationSpec() {

    val feedbackService = mockk<FeedbackService>()
    val funcionarioRepository = mockk<FuncionarioRepository>()

    val feedbackController: FeedbackController = FeedbackController(feedbackService, funcionarioRepository)

    companion object{
        val avaliado = Funcionario(
            "Avaliado Teste",
            "avaliado@email.com",
            "12345678911",
            TipoCargo.VENDEDOR,
            1
        )

        val avaliador = Funcionario(
            "Avaliador Teste",
            "avaliador@email.com",
            "12345678911",
            TipoCargo.GERENTE,
            2
        )

        val feedback = Feedback(
            "Descricao Teste",
            "Titulo Teste",
            avaliado,
            avaliador
        )
        val feedbackAtualizado = Feedback(
            "Descricao Teste Atualizado",
            "Titulo Teste Atualizado",
            avaliado,
            avaliador
        )

    }

    @Test
    fun `deve cadastrar um feedback quando os dados passados forem validos`(){
        every { feedbackService.cadastrar(any()) } answers {
            feedback.id = 1
            feedback
        }

        every { funcionarioRepository.findById(1) } answers { Optional.of(avaliado)}
        every { funcionarioRepository.findById(2) } answers { Optional.of(avaliador)}

        val response = feedbackController.cadastrar(FeedbackRequest(
            "Descricao teste",
            "Titulo teste",
            1,
            2
        ))

        with(response){
            status shouldBe HttpStatus.CREATED
            headers.get("location") shouldBe "/feedbacks/1"
            verify { feedbackService.cadastrar(any()) }
            verify { funcionarioRepository.findById(1) }
            verify { funcionarioRepository.findById(2) }
        }
    }

    @Test
    fun `deve retornar um feedback quando o id informado for de um registro valido`(){

        every { feedbackService.consultar(1) } answers {
            feedback.id = 1
            feedback
        }

        val response = feedbackController.consultar(1)
        with(response){
            status shouldBe HttpStatus.OK
            body.isPresent shouldBe true
            body()!!.titulo shouldBe feedback.titulo
            verify { feedbackService.consultar(1) }
        }

    }

    @Test
    fun `deve retornar uma lista de feedbacks`(){
        every { feedbackService.listar() } answers {
            listOf(FeedbackResponse(feedback), FeedbackResponse(feedback))
        }

        val response = feedbackController.listar()
        with(response){
            status shouldBe HttpStatus.OK
            body()!!.size shouldBe 2
            verify { feedbackService.listar() }
        }
    }

    @Test
    fun `deve deletar um feedback por id`(){
        every { feedbackService.deletar(1) } answers {
            Unit
        }

        val response = feedbackController.deletar(1)
        with(response){
            status shouldBe HttpStatus.OK
            verify { feedbackService.deletar(1) }
        }
    }

    @Test
    fun `deve atualizar um feedback quando o id for de um registro existente`(){
        every { feedbackService.atualizar(1, any()) } answers {
            feedbackAtualizado.id = 1
            feedbackAtualizado
        }

        every { funcionarioRepository.findById(1) } answers { Optional.of(avaliado)}
        every { funcionarioRepository.findById(2) } answers { Optional.of(avaliador)}

        val response = feedbackController.atualizar(1, FeedbackRequest(
            "Descricao teste",
            "Titulo teste",
            1,
            2
        ))

        with(response){
            status shouldBe HttpStatus.OK
            body()!!.let {
                it.titulo shouldBe feedbackAtualizado.titulo
                it.descricao shouldBe feedbackAtualizado.descricao
                it.avaliado shouldBe FuncionarioResponse(avaliado)
                it.avaliador shouldBe FuncionarioResponse(avaliador)
                verify { feedbackService.atualizar(1, any()) }
                verify { funcionarioRepository.findById(1) }
                verify { funcionarioRepository.findById(2) }
            }

        }
    }

}