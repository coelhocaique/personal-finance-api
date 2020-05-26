package com.coelhocaique.finance.api.handler

import com.coelhocaique.finance.api.dto.DebtRequestDTO
import com.coelhocaique.finance.api.helper.LinkBuilder
import com.coelhocaique.finance.core.domain.dto.DebtResponse
import com.coelhocaique.finance.core.service.DebtService
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.server.ServerRequest
import reactor.core.publisher.Mono.just
import reactor.test.StepVerifier
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DebtHandlerTest {

    private val uuid = UUID.randomUUID()
    private val uri = "uri"

    @BeforeAll
    fun mockStatic() {
        mockkObject(RequestParameterHandler)
        mockkObject(LinkBuilder)
    }

    @Test
    fun testCreate() {
        val service = mockk<DebtService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = DebtHandler(service)

        val request = DebtRequestDTO(
                amount = BigDecimal.TEN,
                description = "test",
                type = "money",
                tag = "test",
                debtDate = LocalDate.now(),
                installments = 1,
                accountId = null)

        val response = listOf(mockResponse())

        every { RequestParameterHandler.retrieveAccountId(serverRequest) } answers { just(uuid) }
        every { serverRequest.bodyToMono(DebtRequestDTO::class.java) } answers { just(request) }
        every { serverRequest.uri().toString() } returns uri
        every { LinkBuilder.buildForDebts(serverRequest.uri().toString(), response) } returns just(response)
        every { service.create(any()) } answers { just(response) }

        StepVerifier.create(handler.create(serverRequest))
                .assertNext {
                    assertEquals(HttpStatus.CREATED, it.statusCode())
                }
                .verifyComplete()

        verify(exactly = 1) { service.create(any()) }
        verify(exactly = 1) { LinkBuilder.buildForDebts(eq(uri), eq(response)) }
        verify(exactly = 1) { RequestParameterHandler.retrieveAccountId(eq(serverRequest)) }
        verify(exactly = 1) { serverRequest.bodyToMono(DebtRequestDTO::class.java) }
        verify(exactly = 1) { serverRequest.uri().toString() }
    }

    @Test
    fun testFindById() {
        val service = mockk<DebtService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = DebtHandler(service)
        val fetchCriteria = FetchCriteria(accountId = uuid, id = uuid)

        val response = mockResponse()

        every { RequestParameterHandler.retrievePath(serverRequest) } answers { just(fetchCriteria) }

        every { serverRequest.uri().toString() } returns uri
        every { LinkBuilder.buildForDebt(serverRequest.uri().toString(), response) } returns response
        every { service.findById(any(), any()) } answers { just(response) }

        StepVerifier.create(handler.findById(serverRequest))
                .assertNext {
                    assertEquals(HttpStatus.OK, it.statusCode())
                }
                .verifyComplete()

        verify(exactly = 1) { service.findById(eq(fetchCriteria.accountId), eq(fetchCriteria.id!!)) }
        verify(exactly = 1) { LinkBuilder.buildForDebt(eq(uri), eq(response)) }
        verify(exactly = 1) { RequestParameterHandler.retrievePath(eq(serverRequest)) }
        verify(exactly = 1) { serverRequest.uri().toString() }
    }

    @Test
    fun testFetchDebtsByReferenceDate() {
        val service = mockk<DebtService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = DebtHandler(service)
        val fetchCriteria = FetchCriteria(
                accountId = uuid,
                referenceDate = "202005"
        )

        val response = listOf(mockResponse())

        every { RequestParameterHandler.retrieveParameters(serverRequest) } answers { just(fetchCriteria) }
        every { serverRequest.uri().toString() } returns uri
        every { LinkBuilder.buildForDebts(serverRequest.uri().toString(), response) } returns just(response)
        every { service.findByReferenceDate(any(), any()) } answers { just(response) }

        StepVerifier.create(handler.fetchDebts(serverRequest))
                .assertNext {
                    assertEquals(HttpStatus.OK, it.statusCode())
                }
                .verifyComplete()

        verify(exactly = 1) { service.findByReferenceDate(eq(fetchCriteria.accountId), eq(fetchCriteria.referenceDate!!)) }
        verify(exactly = 1) { LinkBuilder.buildForDebts(eq(uri), eq(response)) }
        verify(exactly = 1) { RequestParameterHandler.retrieveParameters(eq(serverRequest)) }
        verify(exactly = 1) { serverRequest.uri().toString() }
    }

    @Test
    fun testFetchDebtsByReferenceDateRange() {
        val service = mockk<DebtService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = DebtHandler(service)
        val fetchCriteria = FetchCriteria(
                accountId = uuid,
                dateFrom = "202005",
                dateTo = "202005"
        )

        val response = listOf(mockResponse())

        every { RequestParameterHandler.retrieveParameters(serverRequest) } answers { just(fetchCriteria) }
        every { serverRequest.uri().toString() } returns uri
        every { LinkBuilder.buildForDebts(serverRequest.uri().toString(), response) } returns just(response)
        every { service.findByReferenceDateRange(any(), any(), any()) } answers { just(response) }

        StepVerifier.create(handler.fetchDebts(serverRequest))
                .assertNext {
                    assertEquals(HttpStatus.OK, it.statusCode())
                }
                .verifyComplete()

        verify(exactly = 1) { service.findByReferenceDateRange(eq(fetchCriteria.accountId),
                eq(fetchCriteria.dateFrom!!), eq(fetchCriteria.dateTo!!)) }
        verify(exactly = 1) { LinkBuilder.buildForDebts(eq(uri), eq(response)) }
        verify(exactly = 1) { RequestParameterHandler.retrieveParameters(eq(serverRequest)) }
        verify(exactly = 1) { serverRequest.uri().toString() }
    }

    @Test
    fun testFetchDebtsByReferenceCode() {
        val service = mockk<DebtService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = DebtHandler(service)
        val fetchCriteria = FetchCriteria(
                accountId = uuid,
                referenceCode = uuid
        )

        val response = listOf(mockResponse())

        every { RequestParameterHandler.retrieveParameters(serverRequest) } answers { just(fetchCriteria) }
        every { serverRequest.uri().toString() } returns uri
        every { LinkBuilder.buildForDebts(serverRequest.uri().toString(), response) } returns just(response)
        every { service.findByReferenceCode(any(), any()) } answers { just(response) }

        StepVerifier.create(handler.fetchDebts(serverRequest))
                .assertNext {
                    assertEquals(HttpStatus.OK, it.statusCode())
                }
                .verifyComplete()

        verify(exactly = 1) { service.findByReferenceCode(eq(fetchCriteria.accountId), eq(fetchCriteria.referenceCode!!)) }
        verify(exactly = 1) { LinkBuilder.buildForDebts(eq(uri), eq(response)) }
        verify(exactly = 1) { RequestParameterHandler.retrieveParameters(eq(serverRequest)) }
        verify(exactly = 1) { serverRequest.uri().toString() }
    }

    @Test
    fun testFetchDebtsNoCriteria() {
        val service = mockk<DebtService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = DebtHandler(service)
        val fetchCriteria = FetchCriteria(
                accountId = uuid
        )

        every { RequestParameterHandler.retrieveParameters(serverRequest) } answers { just(fetchCriteria) }

        StepVerifier.create(handler.fetchDebts(serverRequest))
                .assertNext {
                    assertEquals(HttpStatus.BAD_REQUEST, it.statusCode())
                }
                .verifyComplete()

        verify(exactly = 1) { RequestParameterHandler.retrieveParameters(eq(serverRequest)) }
        verify(exactly = 0) { serverRequest.uri().toString() }
    }

    @Test
    fun testDeleteById() {
        val service = mockk<DebtService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = DebtHandler(service)
        val fetchCriteria = FetchCriteria(accountId = uuid, id = uuid)

        val response = mockResponse()

        every { RequestParameterHandler.retrievePath(serverRequest) } answers { just(fetchCriteria) }

        every { serverRequest.uri().toString() } returns uri
        every { LinkBuilder.buildForDebt(serverRequest.uri().toString(), response) } returns response
        every { service.deleteById(any(), any()) } answers { just(response) }

        StepVerifier.create(handler.deleteById(serverRequest))
                .assertNext {
                    assertEquals(HttpStatus.NO_CONTENT, it.statusCode())
                }
                .verifyComplete()

        verify(exactly = 1) { service.deleteById(eq(fetchCriteria.accountId), eq(fetchCriteria.id!!)) }
        verify(exactly = 0) { LinkBuilder.buildForDebt(eq(uri), eq(response)) }
        verify(exactly = 1) { RequestParameterHandler.retrievePath(eq(serverRequest)) }
        verify(exactly = 0) { serverRequest.uri().toString() }
    }

    @Test
    fun testDeleteByReferenceCode() {
        val service = mockk<DebtService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = DebtHandler(service)
        val fetchCriteria = FetchCriteria(
                accountId = uuid,
                referenceCode = uuid
        )

        val response = listOf(mockResponse())

        every { RequestParameterHandler.retrieveParameters(serverRequest) } answers { just(fetchCriteria) }
        every { serverRequest.uri().toString() } returns uri
        every { LinkBuilder.buildForDebts(serverRequest.uri().toString(), response) } returns just(response)
        every { service.deleteByReferenceCode(any(), any()) } answers { just(response) }

        StepVerifier.create(handler.delete(serverRequest))
                .assertNext {
                    assertEquals(HttpStatus.NO_CONTENT, it.statusCode())
                }
                .verifyComplete()

        verify(exactly = 1) { service.deleteByReferenceCode(eq(fetchCriteria.accountId), eq(fetchCriteria.referenceCode!!)) }
        verify(exactly = 0) { LinkBuilder.buildForDebts(eq(uri), eq(response)) }
        verify(exactly = 1) { RequestParameterHandler.retrieveParameters(eq(serverRequest)) }
        verify(exactly = 0) { serverRequest.uri().toString() }
    }

    @Test
    fun testDeleteNoCriteria() {
        val service = mockk<DebtService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = DebtHandler(service)
        val fetchCriteria = FetchCriteria(
                accountId = uuid
        )

        every { RequestParameterHandler.retrieveParameters(serverRequest) } answers { just(fetchCriteria) }

        StepVerifier.create(handler.delete(serverRequest))
                .assertNext {
                    assertEquals(HttpStatus.BAD_REQUEST, it.statusCode())
                }
                .verifyComplete()

        verify(exactly = 1) { RequestParameterHandler.retrieveParameters(eq(serverRequest)) }
        verify(exactly = 0) { serverRequest.uri().toString() }
    }

    private fun mockResponse(): DebtResponse {
        return DebtResponse(
                debtId = uuid,
                amount = BigDecimal.TEN,
                description = "test",
                debtDate = LocalDate.now(),
                referenceCode = uuid,
                installmentNumber = 1,
                referenceDate = "202005",
                type = "debt_type",
                tag = "debt_tag",
                installments = 1,
                totalAmount = BigDecimal.TEN,
                creationDate = LocalDateTime.now()
        )
    }
}