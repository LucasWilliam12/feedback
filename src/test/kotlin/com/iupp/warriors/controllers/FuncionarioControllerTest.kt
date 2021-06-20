package com.iupp.warriors.controllers

import com.iupp.warriors.dtos.requests.FuncionarioRequest
import com.iupp.warriors.dtos.responses.FuncionarioResponse
import com.iupp.warriors.enums.TipoCargo
import com.iupp.warriors.models.Funcionario
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpStatus
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

@MicronautTest
internal class FuncionarioControllerTest: AnnotationSpec(){

    val funcionarioService = mockk<FuncionarioService>()
    val funcionarioController = FuncionarioController(funcionarioService)

    companion object{
        val vendedor = Funcionario(
            "Vendedor Teste",
            "vendedor@email.com",
            "34059045012",
            TipoCargo.VENDEDOR
        )

        val gerente = Funcionario(
            "Gerente Teste",
            "gerente@email.com",
            "34059045012",
            TipoCargo.GERENTE
        )

        val vendedorAtualizado = Funcionario(
            "Vendedor Teste Atualizado",
            "vendedoratualizado@email.com",
            "34059045012",
            TipoCargo.VENDEDOR
        )
    }

    @Test
    fun `deve cadastrar um novo funcionario quando os dados passados forem validos`(){

        every { funcionarioService.cadastrar(any()) } answers {
            vendedor.id = 1
            vendedor
        }

       val response = funcionarioController.cadastrar(FuncionarioRequest(
            "Vendedor Teste",
            "vendedor@email.com",
            "34059045012",
            TipoCargo.VENDEDOR
        ))

        with(response){
            status shouldBe HttpStatus.CREATED
            headers.get("location") shouldBe "/funcionarios/1"
            verify { funcionarioService.cadastrar(any()) }
        }
    }

    @Test
    fun `deve atualizar um funcionario quando os dados passados forem validos e o id for de um funcionario existente`(){

        every { funcionarioService.atualizar(1, any()) } answers {
            vendedor.id = 1
            vendedor.nome = vendedorAtualizado.nome
            vendedor.email = vendedorAtualizado.email
            vendedor
        }

        val response = funcionarioController.atualizar(1, FuncionarioRequest(
            "Vendedor Teste Atualizado",
            "vendedoratualizado@email.com",
            "34059045012",
            TipoCargo.VENDEDOR
        ))

        with(response){
            body()!!.let {
                status shouldBe HttpStatus.OK
                it.nome shouldBe vendedorAtualizado.nome
                it.email shouldBe vendedorAtualizado.email
                verify { funcionarioService.atualizar(1, any()) }
            }
        }
    }

    @Test
    fun `deve deletar um funcionario quando o id passado for de um funcionario existente`(){

        every { funcionarioService.deletar(1) } answers {
            Unit
        }

        val response = funcionarioController.deletar(1)

        with(response){
            status shouldBe HttpStatus.OK
            body.isEmpty shouldBe true
            verify { funcionarioService.deletar(1) }
        }
    }

    @Test
    fun `deve retornar um funcionario quando o id passado for de um funcionario existente`(){

        every { funcionarioService.consultar(1) } answers {
            vendedor.id = 1
            vendedor
        }

        val response = funcionarioController.consultar(1)

        with(response){
            status shouldBe HttpStatus.OK
            body()!!.let {
                it.nome shouldBe vendedor.nome
                it.email shouldBe vendedor.email
                it.cargo shouldBe vendedor.cargo
                verify { funcionarioService.consultar(1) }
            }
        }
    }

    @Test
    fun `deve retornar uma lista de funcionarios`(){

        every { funcionarioService.listar() } answers {
            listOf(FuncionarioResponse(vendedor), FuncionarioResponse(gerente))
        }

        val response = funcionarioController.listar()

        with(response){
            status shouldBe HttpStatus.OK
            body()!!.size shouldBe 2
            verify { funcionarioService.listar() }
        }
    }
}