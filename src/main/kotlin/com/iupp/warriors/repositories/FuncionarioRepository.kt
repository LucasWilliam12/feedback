package com.iupp.warriors.repositories

import com.iupp.warriors.models.Funcionario
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

@Repository
interface FuncionarioRepository: JpaRepository<Funcionario, Long>{
}