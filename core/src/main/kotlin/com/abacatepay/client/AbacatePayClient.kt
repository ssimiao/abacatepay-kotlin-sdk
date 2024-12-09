package com.abacatepay.client

import com.abacatepay.model.AbacatePayResponse
import com.abacatepay.model.Billing
import com.abacatepay.model.BillingResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

class AbacatePayClient(
    private val apiKey: String,
    private val engine: HttpClientEngine = CIO.create(),
    private val baseUrl: String = "https://api.abacatepay.com/v1",
    private val httpClient: HttpClient = HttpClient(engine) {
        install(ContentNegotiation) {
            json()
        }
    }
) {
    suspend fun createBilling(billing: Billing): AbacatePayResponse<BillingResponse> {
        return httpClient.post("${baseUrl}/billing/create") {
            contentType(ContentType.Application.Json)
            setBody(billing)
            bearerAuth(apiKey)
        }.body()
    }

    suspend fun listBilling(): AbacatePayResponse<List<BillingResponse>> {
        return httpClient.get("${baseUrl}/billing/list") {
            bearerAuth(apiKey)
        }.body()
    }
}