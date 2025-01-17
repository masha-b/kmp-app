package com.jetbrains.kmpapp.data.remote.api

import com.jetbrains.kmpapp.data.dto.apps.VkpAppsDto
import com.jetbrains.kmpapp.data.dto.apps.VkpBuildsDto
import com.jetbrains.kmpapp.domain.fetch
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod
import io.ktor.http.URLProtocol
import io.ktor.http.appendPathSegments

interface AppsApi {
    suspend fun getAppsByType(type: String): Result<VkpAppsDto?>
    suspend fun getBuildsByAppId(appId: String): Result<VkpBuildsDto?>
}

class AppsApiImpl(private val client: HttpClient) : AppsApi {
    companion object {
        private const val API_URL = "apps.sitesoft.ru/api"
    }

    override suspend fun getAppsByType(type: String): Result<VkpAppsDto?> = client.fetch {
        url {
            method = HttpMethod.Get
            protocol = URLProtocol.HTTPS
            host = API_URL
            appendPathSegments("apps", type)
            parameters.append("token", "SrbxM6TgoFVufyQylcypMO9AHz6BXMOed8kyr7tqEx9xNFcyVIDWFDuAA5wg")
        }
    }

    override suspend fun getBuildsByAppId(appId: String): Result<VkpBuildsDto?> = client.fetch {
        url {
            method = HttpMethod.Get
            protocol = URLProtocol.HTTPS
            host = API_URL
            appendPathSegments("builds")
            parameters.append("id", appId)
            parameters.append("token", "SrbxM6TgoFVufyQylcypMO9AHz6BXMOed8kyr7tqEx9xNFcyVIDWFDuAA5wg")
        }
    }
}