package com.jetbrains.kmpapp.data.remote.mappers

import com.jetbrains.kmpapp.data.dto.errors.FieldErrorDto
import com.jetbrains.kmpapp.data.dto.errors.ValidationErrorDto
import com.jetbrains.kmpapp.domain.exceptions.FieldError
import kotlin.collections.map

object ErrorMapper {

    fun ValidationErrorDto.toDomain(): List<FieldError> =
        errors.map { it.toFieldError() }

    private fun FieldErrorDto.toFieldError(): FieldError =
        FieldError(
            field = field,
            errorText = errorText
        )
}