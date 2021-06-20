package com.iupp.warriors.services

import com.iupp.warriors.enums.TipoCargo
import com.iupp.warriors.models.Funcionario
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.time.LocalDateTime
import java.util.*
import com.iupp.warriors.controllers.handler.exceptions.ObjectNotFoundException

@MicronautTest
internal class FuncionarioServiceImplTest: AnnotationSpec(){

    val funcionarioRepository = mockk<FuncionarioRepository>()
    val funcionarioService = FuncionarioServiceImpl(funcionarioRepository)

    companion object{
        val funcionario = Funcionario(
            "Usuario Teste",
            "usuarioteste@hotmail.com",
            "31365056031",
            TipoCargo.GERENTE,
            1,
            LocalDateTime.now()
        )

        val funcionarioAtualizado = Funcionario(
            "Usuario Atualizado",
            "usuarioteste@hotmail.com",
            "31365056031",
            TipoCargo.VENDEDOR
        )
    }

    @Test
    fun `passando um funcionario com os dados validos para ser cadastrado`() {
        every { funcionarioRepository.save(funcionario) } answers {
            funcionario
        }

        val response = funcionarioService.cadastrar(funcionario)

        with(response) {
            id shouldNotBe null
            nome shouldBe funcionario.nome
            email shouldBe funcionario.email
            cpf shouldBe funcionario.cpf
            verify { funcionarioRepository.save(funcionario) }
        }
    }

    @Test
    fun `passando um funcionario com os dados validos para ser atualizado`() {
        every { funcionarioRepository.update(any()) } answers {
            funcionarioAtualizado.id = 1
            funcionarioAtualizado
        }

        every { funcionarioRepository.findById(1) } answers {
            funcionario.id = 1
            Optional.of(funcionario)
        }

        val response = funcionarioService.atualizar(funcionario.id!!, funcionarioAtualizado)

        with(response) {
            id shouldBe funcionarioAtualizado.id
            nome shouldBe funcionarioAtualizado.nome
            email shouldBe funcionarioAtualizado.email
            cpf shouldBe funcionarioAtualizado.cpf
            cargo shouldBe funcionarioAtualizado.cargo
            verify { funcionarioRepository.update(any()) }
        }
    }

    @Test
    fun `passando um id de funcionario inexistente para ser atualizado`() {
        every { funcionarioRepository.findById(2) } answers {
            Optional.empty()
        }
        val response = shouldThrow<ObjectNotFoundException> {
            funcionarioService.atualizar(2, funcionarioAtualizado)
        }
        with(response) {
            message shouldBe "Usuario não encontrado"
            verify { funcionarioRepository.findById(2) }
        }
    }

    @Test
    fun `passando um id de funcionario existente para ser retornado`() {
        every { funcionarioRepository.findById(1) } answers {
            funcionario.id = 1
            Optional.of(funcionario)
        }

        val response = funcionarioService.consultar(1)

        with(response) {
            id shouldBe 1
            nome shouldBe funcionario.nome
            verify { funcionarioRepository.findById(1) }
        }
    }

    @Test
    fun `passando um id de funcionario inexistente para ser retornado`() {
        every { funcionarioRepository.findById(2) } answers {
            Optional.empty()
        }

        val response = shouldThrow<ObjectNotFoundException> {
            funcionarioService.consultar(2)
        }
        with(response) {
            message shouldBe "Usuario não encontrado"
            verify { funcionarioRepository.findById(2) }
        }
    }

    @Test
    fun `chamando servico que retorna uma lista de funcionarios`() {
        every { funcionarioRepository.findAll() } answers {
            listOf(funcionario, funcionarioAtualizado)
        }

        val response = funcionarioService.listar()

        with(response) {
            size shouldBe 2
            verify { funcionarioRepository.findAll() }
        }
    }

    @Test
    fun `passando um id de funcionario existente para ser deletado`() {
        every { funcionarioRepository.findById(1) } answers {
            funcionario.id = 1
            Optional.of(funcionario)
        }

        every { funcionarioRepository.delete(funcionario) } answers {
            Unit
        }

        val response = funcionarioService.deletar(1)

        response shouldBe Unit
        verify { funcionarioRepository.findById(1) }
    }

    @Test
    fun `passando um id de funcionario inexistente para ser deletado`() {
        every { funcionarioRepository.findById(2) } answers {
            Optional.empty()
        }

        val response = shouldThrow<ObjectNotFoundException> {
            funcionarioService.deletar(2)
        }
        with(response) {
            message shouldBe "Usuario não encontrado"
            verify { funcionarioRepository.findById(2) }
        }
    }
}