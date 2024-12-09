package core

import com.abacatepay.client.AbacatePayClient
import com.abacatepay.model.*
import io.ktor.client.engine.mock.*
import io.ktor.http.*
import io.ktor.utils.io.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import kotlin.test.assertNotNull

class AbacatePayClientTest {

    @Test
    fun shouldCreateBillingAndCustomer() {
        val abacatePayClient: AbacatePayClient = abacatePayClientMock(
            AbacatePayResponse(
                billingResponseTemplate(productResponseTemplate(), customerTemplate())
            )
        )

        val product = Product("id", "produto", "descricao", 1, 100)
        val customer = customerTemplate()

        val billing = Billing(
            PaymentFrequency.ONE_TIME,
            listOf(PaymentMethod.PIX),
            listOf(product),
            "http://voltar",
            "http://completar",
            customer = customer
        )

        runBlocking {
            val createBilling = abacatePayClient.createBilling(billing)
            assertNotNull(createBilling)
        }
    }

    @Test
    fun shouldListBilling() {
        val abacatePayClient: AbacatePayClient = abacatePayClientMock(
            AbacatePayResponse(
                listOf(billingResponseTemplate(productResponseTemplate(), customerTemplate()))
            )
        )

        runBlocking {
            val listBilling = abacatePayClient.listBilling()
            assertNotNull(listBilling)
        }
    }

    private fun productResponseTemplate() = ProductResponse("id", "id", 1)

    private fun customerTemplate() =
        Customer("nome", "1268711192", "clienteteste@gmail.com", "49799449065")

    private fun billingResponseTemplate(productResponse: ProductResponse, customer: Customer) =
        BillingResponse(
            "id",
            "url",
            100,
            PaymentStatus.PENDING,
            true,
            listOf(PaymentMethod.PIX),
            listOf(productResponse),
            PaymentFrequency.ONE_TIME,
            "id",
            "id",
            LocalDateTime.now().toString(),
            LocalDateTime.now().toString(),
            BillingMetadata(
                100,
                "http://voltar",
                "http://completar"
            ),
            Metadata(customer)
        )

    private inline fun <reified T> abacatePayClientMock(response: T) =
        AbacatePayClient(apiKey = "apiKey", engine = MockEngine { _ ->
            respond(
                content = ByteReadChannel(
                    Json.encodeToString(
                        response
                    )
                ),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        })
}