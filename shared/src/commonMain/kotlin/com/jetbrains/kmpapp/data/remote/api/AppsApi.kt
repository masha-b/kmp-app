package com.jetbrains.kmpapp.data.remote.api

import com.jetbrains.kmpapp.data.dto.apps.RequestDto
import com.jetbrains.kmpapp.data.dto.apps.VkpAppsDto
import com.jetbrains.kmpapp.domain.fetch
import io.ktor.client.HttpClient
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import io.ktor.http.URLProtocol
import io.ktor.http.appendEncodedPathSegments
import io.ktor.http.appendPathSegments

interface AppsApi {
    suspend fun getAppsByType(type: String): Result<RequestDto>
}

class AppsApiImpl(private val client: HttpClient) : AppsApi {
    companion object {
        private const val API_URL = "apps.sitesoft.ru/api"
    }

    override suspend fun getAppsByType(type: String): Result<RequestDto> = client.fetch {
        url {
            method = HttpMethod.Get
            protocol = URLProtocol.HTTPS
            host = API_URL
            appendPathSegments("apps", type)
            parameters.append("token", "SrbxM6TgoFVufyQylcypMO9AHz6BXMOed8kyr7tqEx9xNFcyVIDWFDuAA5wg")
        }
    }
}