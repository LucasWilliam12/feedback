package com.iupp.warriors.controllers.handler

import com.iupp.warriors.controllers.handler.exceptions.ActionNotPermited
import com.iupp.warriors.controllers.handler.exceptions.ObjectNotFoundException
import io.micronaut.aop.Around
import io.micronaut.aop.InterceptorBean
import io.micronaut.aop.MethodInterceptor
import io.micronaut.aop.MethodInvocationContext
import io.micronaut.http.HttpResponse
import javax.inject.Singleton
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.*

@Around
@Retention(RUNTIME)
@Target(FUNCTION, CLASS, PROPERTY_GETTER, PROPERTY_SETTER)
annotation class HandlerException()

@InterceptorBean(HandlerException::class)
@Singleton
class HandlerExceptionInterceptor(): MethodInterceptor<Any, Any> {
    override fun intercept(context: MethodInvocationContext<Any, Any>): Any {
        return try{
            context.proceed()
        }catch(e: Exception){
            when(e){
                is ActionNotPermited -> {
                    HttpResponse.badRequest(HandlerDto(e.message!!))
                }
                is ObjectNotFoundException -> {
                    HttpResponse.badRequest(HandlerDto(e.message!!))
                }
                else ->{
                    HttpResponse.serverError()
                }
            }
        }
    }

}