package com.abacatepay.client

import com.abacatepay.exception.AbacatePayGenericException
import com.abacatepay.model.AbacatePayResponse
import com.abacatepay.model.Billing
import com.abacatepay.model.BillingResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

class AbacatePayClient(
    private val apiKey: String,
    private val baseUrl: String = "https://api.abacatepay.com/v1",
    engine: HttpClientEngine = CIO.create(),
) : AbacatePay {

    private val httpClient: HttpClient = HttpClient(engine) {
        install(ContentNegotiation) {
            json()
        }
    }

    override suspend fun createBilling(billing: Billing): AbacatePayResponse<BillingResponse> {
        val response = httpClient.post("${baseUrl}/billing/create") {
            contentType(ContentType.Application.Json)
            setBody(billing)
            bearerAuth(apiKey)
        }

        return getAbacatePayResponse(response)
    }

    override suspend fun listBilling(): AbacatePayResponse<List<BillingResponse>> {
        val response =  httpClient.get("${baseUrl}/billing/list") {
            bearerAuth(apiKey)
        }

        return getAbacatePayResponse(response)
    }

    private suspend inline fun <reified T> getAbacatePayResponse(response: HttpResponse): AbacatePayResponse<T> {
        val abacatePayResponse: AbacatePayResponse<T> = response.body()
        if (!response.status.isSuccess())
            throw AbacatePayGenericException(abacatePayResponse.error.orEmpty())

        return abacatePayResponse
    }
}