package com.yjotdev.empprimaria.infrastructure.repositories

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.empprimaria.domain.entity.EmailEntity
import com.yjotdev.empprimaria.domain.port.EmailRepository
import com.yjotdev.empprimaria.infrastructure.adapter.Api

@Singleton
class EmailRepositoryImpl @Inject constructor(
    private val api: Api
): EmailRepository {
    /** Enviar un comentario **/
    override suspend fun sendCommentary(email: EmailEntity) {
        return api.getEmailRetrofit().sendCommentary(email)
    }

    /** Enviar un correo electronico **/
    override suspend fun sendEmail(email: EmailEntity) {
        return api.getEmailRetrofit().sendEmail(email)
    }
}