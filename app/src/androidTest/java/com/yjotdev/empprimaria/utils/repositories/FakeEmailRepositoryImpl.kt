package com.yjotdev.empprimaria.utils.repositories

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.empprimaria.domain.model.EmailModel
import com.yjotdev.empprimaria.domain.repository.EmailRepository
import com.yjotdev.empprimaria.domain.core.Result

@Singleton
class FakeEmailRepositoryImpl @Inject constructor(): EmailRepository {

    override suspend fun sendEmail(email: EmailModel): Result<Unit> {
        return if(email != EmailModel()) {
            Result.Success(Unit)
        } else {
            Result.Error(Exception("Error al enviar el email"))
        }
    }
}