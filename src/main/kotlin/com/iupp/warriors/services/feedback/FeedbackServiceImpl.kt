package com.iupp.warriors.services.feedback

import com.iupp.warriors.dtos.responses.FeedbackResponse
import com.iupp.warriors.models.Feedback
import com.iupp.warriors.repositories.FeedbackRepository
import com.iupp.warriors.utils.exceptions.ActionNotPermited
import com.iupp.warriors.utils.exceptions.ObjectNotFoundException
import io.micronaut.validation.Validated
import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional
import javax.validation.Valid

@Validated
@Singleton
class FeedbackServiceImpl(
    @Inject val feedbackRepository: FeedbackRepository
): FeedbackService {

    @Transactional
    override fun cadastrar(@Valid request: Feedback): Feedback {
        println(request.avaliador.cargo.permitido())
        if(request.avaliador.cargo.permitido()){
            return feedbackRepository.save(request)
        }else{
            throw ActionNotPermited("Funcionario do tipo vendedor não pode cadastrar feedbacks")
        }
    }

    @Transactional
    override fun consultar(id: Long): Feedback {
        return feedbackRepository.findById(id).orElseThrow{ObjectNotFoundException("Feedback não encontrado com o id informado")}
    }

    @Transactional
    override fun listar(): List<FeedbackResponse> {
        return feedbackRepository.findAll().map {
            FeedbackResponse(it)
        }
    }

    @Transactional
    override fun atualizar(id: Long, @Valid request: Feedback): Feedback {
        val feedback = this.consultar(id)
        if(request.avaliador.cargo.permitido()){
            feedback.atualiza(request)
        }else{
            throw ActionNotPermited("Funcionario do tipo vendedor não pode cadastrar feedbacks")
        }
        return feedbackRepository.update(feedback)
    }

    @Transactional
    override fun deletar(id: Long) {
        val feedback = this.consultar(id)
        feedbackRepository.delete(feedback)
    }
}