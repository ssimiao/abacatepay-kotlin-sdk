package com.abacatepay.client

import com.abacatepay.model.AbacatePayResponse
import com.abacatepay.model.Billing
import com.abacatepay.model.BillingResponse

interface AbacatePay {

    suspend fun createBilling(billing: Billing): AbacatePayResponse<BillingResponse>

    suspend fun listBilling(): AbacatePayResponse<List<BillingResponse>>
}