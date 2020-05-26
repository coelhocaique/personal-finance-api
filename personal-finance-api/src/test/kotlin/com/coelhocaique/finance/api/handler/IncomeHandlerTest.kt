package com.coelhocaique.finance.api.handler

import com.coelhocaique.finance.api.dto.IncomeRequestDTO
import com.coelhocaique.finance.api.helper.LinkBuilder
import com.coelhocaique.finance.core.domain.dto.IncomeResponse
import com.coelhocaique.finance.core.service.IncomeService
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
class IncomeHandlerTest {

    private val uuid = UUID.randomUUID()
    private val uri = "uri"

    @BeforeAll
    fun mockStatic() {
        mockkObject(RequestParameterHandler)
        mockkObject(LinkBuilder)
    }

    @Test
    fun testCreate() {
        val service = mockk<IncomeService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = IncomeHandler(service)

        val request = IncomeRequestDTO(
                grossAmount = BigDecimal.TEN,
                description = "test",
                receiptDate = LocalDate.now(),
                sourceName = "test",
                referenceDate = LocalDate.now(),
                accountId = null)

        val response = mockResponse()

        every { RequestParameterHandler.retrieveAccountId(serverRequest) } answers { just(uuid) }
        every { serverRequest.bodyToMono(IncomeRequestDTO::class.java) } answers { just(request) }
        every { serverRequest.uri().toString() } returns uri
        every { LinkBuilder.buildForIncome(serverRequest.uri().toString(), response) } returns response
        every { service.create(any()) } answers { just(response) }

        StepVerifier.create(handler.create(serverRequest))
                .assertNext {
                    assertEquals(HttpStatus.CREATED, it.statusCode())
                }
                .verifyComplete()

        verify(exactly = 1) { service.create(any()) }
        verify(exactly = 1) { LinkBuilder.buildForIncome(eq(uri), eq(response)) }
        verify(exactly = 1) { RequestParameterHandler.retrieveAccountId(eq(serverRequest)) }
        verify(exactly = 1) { serverRequest.bodyToMono(IncomeRequestDTO::class.java) }
        verify(exactly = 1) { serverRequest.uri().toString() }
    }

    @Test
    fun testFindById() {
        val service = mockk<IncomeService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = IncomeHandler(service)
        val fetchCriteria = FetchCriteria(accountId = uuid, id = uuid)

        val response = mockResponse()

        every { RequestParameterHandler.retrievePath(serverRequest) } answers { just(fetchCriteria) }

        every { serverRequest.uri().toString() } returns uri
        every { LinkBuilder.buildForIncome(serverRequest.uri().toString(), response) } returns response
        every { service.findById(any(), any()) } answers { just(response) }

        StepVerifier.create(handler.findById(serverRequest))
                .assertNext {
                    assertEquals(HttpStatus.OK, it.statusCode())
                }
                .verifyComplete()

        verify(exactly = 1) { service.findById(eq(fetchCriteria.accountId), eq(fetchCriteria.id!!)) }
        verify(exactly = 1) { LinkBuilder.buildForIncome(eq(uri), eq(response)) }
        verify(exactly = 1) { RequestParameterHandler.retrievePath(eq(serverRequest)) }
        verify(exactly = 1) { serverRequest.uri().toString() }
    }

    @Test
    fun testFetchIncomesByReferenceDate() {
        val service = mockk<IncomeService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = IncomeHandler(service)
        val fetchCriteria = FetchCriteria(
                accountId = uuid,
                referenceDate = "202005"
        )

        val response = listOf(mockResponse())

        every { RequestParameterHandler.retrieveParameters(serverRequest) } answers { just(fetchCriteria) }
        every { serverRequest.uri().toString() } returns uri
        every { LinkBuilder.buildForIncomes(serverRequest.uri().toString(), response) } returns just(response)
        every { service.findByReferenceDate(any(), any()) } answers { just(response) }

        StepVerifier.create(handler.fetchIncomes(serverRequest))
                .assertNext {
                    assertEquals(HttpStatus.OK, it.statusCode())
                }
                .verifyComplete()

        verify(exactly = 1) { service.findByReferenceDate(eq(fetchCriteria.accountId), eq(fetchCriteria.referenceDate!!)) }
        verify(exactly = 1) { LinkBuilder.buildForIncomes(eq(uri), eq(response)) }
        verify(exactly = 1) { RequestParameterHandler.retrieveParameters(eq(serverRequest)) }
        verify(exactly = 1) { serverRequest.uri().toString() }
    }

    @Test
    fun testFetchIncomesByReferenceDateRange() {
        val service = mockk<IncomeService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = IncomeHandler(service)
        val fetchCriteria = FetchCriteria(
                accountId = uuid,
                dateFrom = "202005",
                dateTo = "202005"
        )

        val response = listOf(mockResponse())

        every { RequestParameterHandler.retrieveParameters(serverRequest) } answers { just(fetchCriteria) }
        every { serverRequest.uri().toString() } returns uri
        every { LinkBuilder.buildForIncomes(serverRequest.uri().toString(), response) } returns just(response)
        every { service.findByReferenceDateRange(any(), any(), any()) } answers { just(response) }

        StepVerifier.create(handler.fetchIncomes(serverRequest))
                .assertNext {
                    assertEquals(HttpStatus.OK, it.statusCode())
                }
                .verifyComplete()

        verify(exactly = 1) { service.findByReferenceDateRange(eq(fetchCriteria.accountId),
                eq(fetchCriteria.dateFrom!!), eq(fetchCriteria.dateTo!!)) }
        verify(exactly = 1) { LinkBuilder.buildForIncomes(eq(uri), eq(response)) }
        verify(exactly = 1) { RequestParameterHandler.retrieveParameters(eq(serverRequest)) }
        verify(exactly = 1) { serverRequest.uri().toString() }
    }

    @Test
    fun testFetchIncomesNoCriteria() {
        val service = mockk<IncomeService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = IncomeHandler(service)
        val fetchCriteria = FetchCriteria(
                accountId = uuid
        )

        every { RequestParameterHandler.retrieveParameters(serverRequest) } answers { just(fetchCriteria) }

        StepVerifier.create(handler.fetchIncomes(serverRequest))
                .assertNext {
                    assertEquals(HttpStatus.BAD_REQUEST, it.statusCode())
                }
                .verifyComplete()

        verify(exactly = 1) { RequestParameterHandler.retrieveParameters(eq(serverRequest)) }
        verify(exactly = 0) { serverRequest.uri().toString() }
    }

    @Test
    fun testDeleteById() {
        val service = mockk<IncomeService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = IncomeHandler(service)
        val fetchCriteria = FetchCriteria(accountId = uuid, id = uuid)

        val response = mockResponse()

        every { RequestParameterHandler.retrievePath(serverRequest) } answers { just(fetchCriteria) }

        every { serverRequest.uri().toString() } returns uri
        every { LinkBuilder.buildForIncome(serverRequest.uri().toString(), response) } returns response
        every { service.deleteById(any(), any()) } answers { just(response) }

        StepVerifier.create(handler.deleteById(serverRequest))
                .assertNext {
                    assertEquals(HttpStatus.NO_CONTENT, it.statusCode())
                }
                .verifyComplete()

        verify(exactly = 1) { service.deleteById(eq(fetchCriteria.accountId), eq(fetchCriteria.id!!)) }
        verify(exactly = 0) { LinkBuilder.buildForIncome(eq(uri), eq(response)) }
        verify(exactly = 1) { RequestParameterHandler.retrievePath(eq(serverRequest)) }
        verify(exactly = 0) { serverRequest.uri().toString() }
    }

    private fun mockResponse(): IncomeResponse {
        return IncomeResponse(
                incomeId = uuid,
                grossAmount = BigDecimal.TEN,
                netAmount = BigDecimal.TEN,
                additionalAmount = BigDecimal.ZERO,
                description = "test",
                receiptDate = LocalDate.now(),
                referenceDate = "202005",
                sourceName = "test",
                discountAmount = BigDecimal.ZERO,
                creationDate = LocalDateTime.now()
        )
    }
}