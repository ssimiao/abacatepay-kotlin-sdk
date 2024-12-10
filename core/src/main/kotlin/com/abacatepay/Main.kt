package com.abacatepay

import com.abacatepay.client.AbacatePayClient

suspend fun main() {
    AbacatePayClient(apiKey = "oi").listBilling()
}
