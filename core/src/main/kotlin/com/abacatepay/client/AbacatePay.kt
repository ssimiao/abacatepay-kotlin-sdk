package com.abacatepay.client

import com.abacatepay.model.*

interface AbacatePay {

    suspend fun createBilling(billing: Billing): AbacatePayResponse<BillingResponse>

    suspend fun listBillings(): AbacatePayResponse<List<BillingResponse>>

    suspend fun createCustomer(customer: Customer): AbacatePayResponse<CreateCustomerResponse>

    suspend fun listCustomers(): AbacatePayResponse<List<CustomerResponse>>
}