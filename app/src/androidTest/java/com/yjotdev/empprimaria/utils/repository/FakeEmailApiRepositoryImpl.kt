package com.yjotdev.empprimaria.utils.repository

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.empprimaria.domain.model.EmailModel
import com.yjotdev.empprimaria.domain.repository.EmailApiRepository
import com.yjotdev.empprimaria.domain.core.Result

@Singleton
class FakeEmailApiRepositoryImpl @Inject constructor(): EmailApiRepository {

    override suspend fun sendEmail(email: EmailModel): Result<Unit> {
        return if(email != EmailModel()) {
            Result.Success(Unit)
        } else {
            Result.Error(Exception("Error al enviar el email"))
        }
    }
}