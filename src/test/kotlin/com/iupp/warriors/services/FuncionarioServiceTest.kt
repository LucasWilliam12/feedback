package com.iupp.warriors.services

import com.iupp.warriors.enums.TipoCargo
import com.iupp.warriors.models.Funcionario
import com.iupp.warriors.repositories.FuncionarioRepository
import com.iupp.warriors.services.funcionario.FuncionarioServiceImpl
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
internal class FuncionarioServiceTest(
    val funcionarioRepository: FuncionarioRepository,
): BehaviorSpec({
    given("metodos services responsaveis por cadastrar retornar e alterar funcionarios") {
        val funcionario = Funcionario(
            "Usuario Teste",
            "usuarioteste@hotmail.com",
            "31365056031",
            TipoCargo.GERENTE
        )

        val createdAt = LocalDateTime.now()
        val repositoryMock = getMock(funcionarioRepository)
        val funcionarioService = FuncionarioServiceImpl(repositoryMock)

        every { repositoryMock.save(funcionario) } answers {
            funcionario.id = 1
            funcionario.createdAt = createdAt
            funcionario
        }

        `when`("passando um funcionario com os dados validos para ser cadastrado") {
            then("retorna um funcionario com id") {
                val response = funcionarioService.cadastrar(funcionario)
                response.id shouldNotBe null
                response.nome shouldBe funcionario.nome
                response.email shouldBe funcionario.email
                response.cpf shouldBe funcionario.cpf
                verify { repositoryMock.save(funcionario) }
            }
        }

        val funcionarioAtualizado = Funcionario(
            "Usuario Atualizado",
            "usuarioteste@hotmail.com",
            "31365056031",
            TipoCargo.VENDEDOR
        )

        every { repositoryMock.update(funcionarioAtualizado) } answers {
            funcionarioAtualizado.id = 1
            funcionarioAtualizado
        }

        every { repositoryMock.findById(1) } answers {
            funcionario.id = 1
            Optional.of(funcionario)
        }

        `when`("passando um funcionario com os dados validos para ser atualizado") {
            then("retorna um funcionario com os valores alterados") {
                val response = funcionarioService.atualizar(funcionario.id!!, funcionarioAtualizado)
                response.id shouldBe funcionarioAtualizado.id
                response.nome shouldBe funcionarioAtualizado.nome
                response.email shouldBe funcionarioAtualizado.email
                response.cpf shouldBe funcionarioAtualizado.cpf
                response.cargo shouldBe funcionarioAtualizado.cargo
                verify { repositoryMock.update(funcionarioAtualizado) }
            }
        }

        every { repositoryMock.findById(2) } answers {
            Optional.empty()
        }

        `when`("passando um id de funcionario inexistente para ser atualizado") {
            val response = shouldThrow<ObjectNotFoundException> {
                funcionarioService.atualizar(2, funcionarioAtualizado)
            }
            then("retorna uma excecao ObjectNotFoundException") {
                response.message shouldBe "Usuario não encontrado"
                verify { repositoryMock.findById(2) }
            }
        }

        `when`("passando um id de funcionario existente para ser retornado") {
            then("retorna um funcionario") {
                val response = funcionarioService.consultar(1)
                response.id shouldBe 1
                response.nome shouldBe funcionario.nome
                verify { repositoryMock.findById(1) }
            }
        }

        `when`("passando um id de funcionario inexistente para ser retornado") {
            val response = shouldThrow<ObjectNotFoundException> {
                funcionarioService.consultar(2)
            }
            then("retorna uma excecao ObjectNotFoundException") {
                response.message shouldBe "Usuario não encontrado"
                verify { repositoryMock.findById(2) }
            }
        }

        every { repositoryMock.findAll() } answers {
            listOf(funcionario, funcionarioAtualizado)
        }

        `when`("chamando servico que retorna uma lista de funcionarios") {
            then("retorna uma lista de funcionarios") {

                val response = funcionarioService.listar()

                response.size shouldBe 2
                verify { repositoryMock.findAll() }
            }
        }
    }
}){

    @MockBean(FuncionarioRepository::class)
    fun funcionarioRepository(): FuncionarioRepository {
        return mockk()
    }
}