package com.coelhocaique.finance.api.handler

import com.coelhocaique.finance.api.dto.RecurringDebtRequestDTO
import com.coelhocaique.finance.api.helper.LinkBuilder
import com.coelhocaique.finance.core.domain.dto.RecurringDebtResponse
import com.coelhocaique.finance.core.service.RecurringDebtService
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
import java.time.LocalDateTime
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RecurringDebtHandlerTest {

    private val uuid = UUID.randomUUID()
    private val uri = "uri"

    @BeforeAll
    fun mockStatic() {
        mockkObject(RequestParameterHandler)
        mockkObject(LinkBuilder)
    }

    @Test
    fun testCreate() {
        val service = mockk<RecurringDebtService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = RecurringDebtHandler(service)

        val request = RecurringDebtRequestDTO(
                amount = BigDecimal.TEN,
                description = "test",
                type = "money",
                tag = "test",
                accountId = null)

        val response = mockResponse()

        every { RequestParameterHandler.retrieveAccountId(serverRequest) } answers { just(uuid) }
        every { serverRequest.bodyToMono(RecurringDebtRequestDTO::class.java) } answers { just(request) }
        every { serverRequest.uri().toString() } returns uri
        every { LinkBuilder.buildForRecurringDebt(serverRequest.uri().toString(), response) } returns response
        every { service.create(any()) } answers { just(response) }

        StepVerifier.create(handler.create(serverRequest))
                .assertNext {
                    assertEquals(HttpStatus.CREATED, it.statusCode())
                }
                .verifyComplete()

        verify(exactly = 1) { service.create(any()) }
        verify(exactly = 1) { LinkBuilder.buildForRecurringDebt(eq(uri), eq(response)) }
        verify(exactly = 1) { RequestParameterHandler.retrieveAccountId(eq(serverRequest)) }
        verify(exactly = 1) { serverRequest.bodyToMono(RecurringDebtRequestDTO::class.java) }
        verify(exactly = 1) { serverRequest.uri().toString() }
    }

    @Test
    fun testFindById() {
        val service = mockk<RecurringDebtService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = RecurringDebtHandler(service)
        val fetchCriteria = FetchCriteria(accountId = uuid, id = uuid)

        val response = mockResponse()

        every { RequestParameterHandler.retrievePath(serverRequest) } answers { just(fetchCriteria) }

        every { serverRequest.uri().toString() } returns uri
        every { LinkBuilder.buildForRecurringDebt(serverRequest.uri().toString(), response) } returns response
        every { service.findById(any(), any()) } answers { just(response) }

        StepVerifier.create(handler.findById(serverRequest))
                .assertNext {
                    assertEquals(HttpStatus.OK, it.statusCode())
                }
                .verifyComplete()

        verify(exactly = 1) { service.findById(eq(fetchCriteria.accountId), eq(fetchCriteria.id!!)) }
        verify(exactly = 1) { LinkBuilder.buildForRecurringDebt(eq(uri), eq(response)) }
        verify(exactly = 1) { RequestParameterHandler.retrievePath(eq(serverRequest)) }
        verify(exactly = 1) { serverRequest.uri().toString() }
    }

    @Test
    fun testFetchAllRecurringDebts() {
        val service = mockk<RecurringDebtService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = RecurringDebtHandler(service)
        val fetchCriteria = FetchCriteria(
                accountId = uuid
        )

        val response = listOf(mockResponse())

        every { RequestParameterHandler.retrieveParameters(serverRequest) } answers { just(fetchCriteria) }
        every { serverRequest.uri().toString() } returns uri
        every { LinkBuilder.buildForRecurringDebts(serverRequest.uri().toString(), response) } returns just(response)
        every { service.findAll(any()) } answers { just(response) }

        StepVerifier.create(handler.fetchAll(serverRequest))
                .assertNext {
                    assertEquals(HttpStatus.OK, it.statusCode())
                }
                .verifyComplete()

        verify(exactly = 1) { service.findAll(eq(fetchCriteria.accountId)) }
        verify(exactly = 1) { LinkBuilder.buildForRecurringDebts(eq(uri), eq(response)) }
        verify(exactly = 1) { RequestParameterHandler.retrieveParameters(eq(serverRequest)) }
        verify(exactly = 1) { serverRequest.uri().toString() }
    }

    @Test
    fun testDeleteById() {
        val service = mockk<RecurringDebtService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = RecurringDebtHandler(service)
        val fetchCriteria = FetchCriteria(accountId = uuid, id = uuid)

        val response = mockResponse()

        every { RequestParameterHandler.retrievePath(serverRequest) } answers { just(fetchCriteria) }

        every { serverRequest.uri().toString() } returns uri
        every { LinkBuilder.buildForRecurringDebt(serverRequest.uri().toString(), response) } returns response
        every { service.deleteById(any(), any()) } answers { just(response) }

        StepVerifier.create(handler.deleteById(serverRequest))
                .assertNext {
                    assertEquals(HttpStatus.NO_CONTENT, it.statusCode())
                }
                .verifyComplete()

        verify(exactly = 1) { service.deleteById(eq(fetchCriteria.accountId), eq(fetchCriteria.id!!)) }
        verify(exactly = 0) { LinkBuilder.buildForRecurringDebt(eq(uri), eq(response)) }
        verify(exactly = 1) { RequestParameterHandler.retrievePath(eq(serverRequest)) }
        verify(exactly = 0) { serverRequest.uri().toString() }
    }

    private fun mockResponse(): RecurringDebtResponse {
        return RecurringDebtResponse(
                recurringDebtId = uuid,
                amount = BigDecimal.TEN,
                description = "test",
                type = "money",
                tag = "test",
                creationDate = LocalDateTime.now()
        )
    }
}