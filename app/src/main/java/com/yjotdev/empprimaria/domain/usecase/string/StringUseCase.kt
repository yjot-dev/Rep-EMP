package com.yjotdev.empprimaria.domain.usecase.string

import javax.inject.Inject
import javax.inject.Singleton
import com.yjotdev.empprimaria.domain.port.StringPort

@Singleton
class StringUseCase @Inject constructor(
    private val stringPort: StringPort
) {
    operator fun invoke(resId: Int): String {
        return stringPort.getString(resId)
    }
}