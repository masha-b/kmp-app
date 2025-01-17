package com.jetbrains.kmpapp.domain

import com.jetbrains.kmpapp.constants.ErrorCodes
import com.jetbrains.kmpapp.data.dto.base.ServerResponse
import com.jetbrains.kmpapp.domain.exceptions.ServerException
import com.jetbrains.kmpapp.domain.exceptions.UnauthorizedException
import com.jetbrains.kmpapp.domain.exceptions.UnknownException
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.request
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode

suspend inline fun <reified T> HttpClient.fetch(
    block: HttpRequestBuilder.() -> Unit
): Result<T?> = try {
    val response = request(block)
    if (response.status == HttpStatusCode.OK) {
        val body = response.body<ServerResponse<T>>()
        when {
            body.isSuccessful -> Result.success(body.getData<T?>())
            body.errorCode == ErrorCodes.UNAUTHORIZED_CODE -> Result.failure(UnauthorizedException())
            else -> Result.failure(ServerException(body.errorCode.toString(), body.errorName))
        }
    } else {
        Napier.e("${response.status}: ${response.bodyAsText()}")
        Result.failure(UnknownException())
    }
} catch (e: Exception) {
    Napier.e(e.message.toString(), e)
    Result.failure(e)
}

suspend inline fun <reified T> HttpClient.exampleFetchForGet(
    url: String,
): Result<T?> = try {
    val response = get(url)
    if (response.status == HttpStatusCode.OK) {
        val body = response.body<ServerResponse<T>>()
        when {
            body.isSuccessful -> Result.success(body.getData<T?>())
            body.errorCode == ErrorCodes.UNAUTHORIZED_CODE -> Result.failure(UnauthorizedException())
            else -> Result.failure(ServerException(body.errorCode.toString(), body.errorName))
        }
    } else {
        Napier.e("${response.status}: ${response.bodyAsText()}")
        Result.failure(UnknownException())
    }
} catch (e: Exception) {
    Napier.e(e.message.toString(), e)
    Result.failure(e)
}

suspend inline fun <reified T> HttpClient.fetchForGet(
    url: String,
): Result<T?> = try {
    val response = get(url)
    if (response.status == HttpStatusCode.OK) {
        val body = response.body<ServerResponse<T>>()
        when {
            body.isSuccessful -> Result.success(body.getData<T?>())
            body.errorCode == ErrorCodes.UNAUTHORIZED_CODE -> Result.failure(UnauthorizedException())
            else -> Result.failure(ServerException(body.errorCode.toString(), body.errorName))
        }
    } else {
        Napier.e("${response.status}: ${response.bodyAsText()}")
        Result.failure(UnknownException())
    }
} catch (e: Exception) {
    Napier.e(e.message.toString(), e)
    Result.failure(e)
}

inline fun <reified R> Result<R>.takeIfSuccess(): R? =
    if (this.isSuccess) this.getOrNull() else null

inline fun <reified R> Result<R>.handle(
    onSuccess: (R) -> Unit,
    onError: (Throwable) -> Unit
): Unit = if (this.isSuccess) {
    try {
        val value = getOrThrow()
        onSuccess(value)
    } catch (e: Exception) {
        onError(e)
    }
} else {
    onError(this.exceptionOrNull() ?: Throwable())
}

inline fun <reified R, reified T> Result<R>.handleMap(
    onSuccess: (R) -> T,
    onError: (Throwable) -> T
): T = if (this.isSuccess) {
    try {
        val value = getOrThrow()
        onSuccess(value)
    } catch (e: Exception) {
        onError(e)
    }
} else {
    onError(this.exceptionOrNull() ?: Throwable())
}

inline fun <reified R, reified T> Result<R>.map(mapper: (R) -> T): Result<T> =
    if (this.isSuccess) {
        try {
            val value = getOrThrow()
            Result.success(mapper(value))
        } catch (e: Exception) {
            Result.failure(e)
        }
    } else {
        Result.failure(this.exceptionOrNull() ?: Throwable())
    }