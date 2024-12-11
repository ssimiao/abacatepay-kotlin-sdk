package com.abacatepay.model

import kotlinx.serialization.Serializable

@Serializable
data class Customer(
    val name: String,
    val cellphone: String,
    val email: String,
    val taxId: String
)

@Serializable
data class Product(
    val externalId: String,
    val name: String,
    val description: String,
    val quantity: Int,
    val price: Int
)

@Serializable
data class ProductResponse(
    val externalId: String,
    val id: String,
    val quantity: Int,
)

@Serializable
data class Billing(
    val frequency: PaymentFrequency,
    val methods: List<PaymentMethod>,
    val products: List<Product>,
    val returnUrl: String,
    val completionUrl: String,
    val customerId: String? = null,
    val customer: Customer? = null
)

@Serializable
enum class PaymentFrequency {
    ONE_TIME
}

@Serializable
enum class PaymentMethod {
    PIX
}

@Serializable
data class BillingResponse(
    val id: String,
    val url: String,
    val amount: Int,
    val status: PaymentStatus,
    val devMode: Boolean,
    val methods: List<PaymentMethod>,
    val products: List<ProductResponse>,
    val frequency: PaymentFrequency,
    val accountId: String,
    val storeId: String,
    val createdAt: String,
    val updatedAt: String,
    val metadata: BillingMetadata,
    val customer: Metadata<Customer>
)

@Serializable
data class BillingMetadata(
    val fee: Int,
    val returnUrl: String,
    val completionUrl: String
)

@Serializable
data class AbacatePayResponse<T> (
    val data: T? = null,
    val error: String? = null
)

@Serializable
data class Metadata<T> (
    val metadata: T
)

@Serializable
enum class PaymentStatus {
    PENDING, EXPIRED, CANCELLED, PAID, REFUNDED
}