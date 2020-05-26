package com.coelhocaique.finance.api.handler

import com.coelhocaique.finance.api.dto.ParameterRequestDTO
import com.coelhocaique.finance.api.helper.LinkBuilder
import com.coelhocaique.finance.core.domain.dto.ParameterResponse
import com.coelhocaique.finance.core.service.ParameterService
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
class ParameterHandlerTest {

    private val uuid = UUID.randomUUID()
    private val uri = "uri"

    @BeforeAll
    fun mockStatic() {
        mockkObject(RequestParameterHandler)
        mockkObject(LinkBuilder)
    }

    @Test
    fun testCreate() {
        val service = mockk<ParameterService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = ParameterHandler(service)

        val request = ParameterRequestDTO(
                name = "threshold",
                value = BigDecimal.TEN.toString(),
                referenceDate = LocalDate.now(),
                accountId = null)

        val response = mockResponse()

        every { RequestParameterHandler.retrieveAccountId(serverRequest) } answers { just(uuid) }
        every { serverRequest.bodyToMono(ParameterRequestDTO::class.java) } answers { just(request) }
        every { serverRequest.uri().toString() } returns uri
        every { LinkBuilder.buildForParameter(serverRequest.uri().toString(), response) } returns response
        every { service.create(any()) } answers { just(response) }

        StepVerifier.create(handler.create(serverRequest))
                .assertNext {
                    assertEquals(HttpStatus.CREATED, it.statusCode())
                }
                .verifyComplete()

        verify(exactly = 1) { service.create(any()) }
        verify(exactly = 1) { LinkBuilder.buildForParameter(eq(uri), eq(response)) }
        verify(exactly = 1) { RequestParameterHandler.retrieveAccountId(eq(serverRequest)) }
        verify(exactly = 1) { serverRequest.bodyToMono(ParameterRequestDTO::class.java) }
        verify(exactly = 1) { serverRequest.uri().toString() }
    }

    @Test
    fun testFindById() {
        val service = mockk<ParameterService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = ParameterHandler(service)
        val fetchCriteria = FetchCriteria(accountId = uuid, id = uuid)

        val response = mockResponse()

        every { RequestParameterHandler.retrievePath(serverRequest) } answers { just(fetchCriteria) }

        every { serverRequest.uri().toString() } returns uri
        every { LinkBuilder.buildForParameter(serverRequest.uri().toString(), response) } returns response
        every { service.findById(any(), any()) } answers { just(response) }

        StepVerifier.create(handler.findById(serverRequest))
                .assertNext {
                    assertEquals(HttpStatus.OK, it.statusCode())
                }
                .verifyComplete()

        verify(exactly = 1) { service.findById(eq(fetchCriteria.accountId), eq(fetchCriteria.id!!)) }
        verify(exactly = 1) { LinkBuilder.buildForParameter(eq(uri), eq(response)) }
        verify(exactly = 1) { RequestParameterHandler.retrievePath(eq(serverRequest)) }
        verify(exactly = 1) { serverRequest.uri().toString() }
    }

    @Test
    fun testFetchParametersByReferenceDate() {
        val service = mockk<ParameterService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = ParameterHandler(service)
        val fetchCriteria = FetchCriteria(
                accountId = uuid,
                referenceDate = "202005"
        )

        val response = listOf(mockResponse())

        every { RequestParameterHandler.retrieveParameters(serverRequest) } answers { just(fetchCriteria) }
        every { serverRequest.uri().toString() } returns uri
        every { LinkBuilder.buildForParameters(serverRequest.uri().toString(), response) } returns just(response)
        every { service.findByReferenceDate(any(), any()) } answers { just(response) }

        StepVerifier.create(handler.fetchParameters(serverRequest))
                .assertNext {
                    assertEquals(HttpStatus.OK, it.statusCode())
                }
                .verifyComplete()

        verify(exactly = 1) { service.findByReferenceDate(eq(fetchCriteria.accountId), eq(fetchCriteria.referenceDate!!)) }
        verify(exactly = 1) { LinkBuilder.buildForParameters(eq(uri), eq(response)) }
        verify(exactly = 1) { RequestParameterHandler.retrieveParameters(eq(serverRequest)) }
        verify(exactly = 1) { serverRequest.uri().toString() }
    }

    @Test
    fun testFetchParametersByReferenceDateRange() {
        val service = mockk<ParameterService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = ParameterHandler(service)
        val fetchCriteria = FetchCriteria(
                accountId = uuid,
                dateFrom = "202005",
                dateTo = "202005"
        )

        val response = listOf(mockResponse())

        every { RequestParameterHandler.retrieveParameters(serverRequest) } answers { just(fetchCriteria) }
        every { serverRequest.uri().toString() } returns uri
        every { LinkBuilder.buildForParameters(serverRequest.uri().toString(), response) } returns just(response)
        every { service.findByReferenceDateRange(any(), any(), any()) } answers { just(response) }

        StepVerifier.create(handler.fetchParameters(serverRequest))
                .assertNext {
                    assertEquals(HttpStatus.OK, it.statusCode())
                }
                .verifyComplete()

        verify(exactly = 1) { service.findByReferenceDateRange(eq(fetchCriteria.accountId),
                eq(fetchCriteria.dateFrom!!), eq(fetchCriteria.dateTo!!)) }
        verify(exactly = 1) { LinkBuilder.buildForParameters(eq(uri), eq(response)) }
        verify(exactly = 1) { RequestParameterHandler.retrieveParameters(eq(serverRequest)) }
        verify(exactly = 1) { serverRequest.uri().toString() }
    }

    @Test
    fun testFetchParametersByName() {
        val service = mockk<ParameterService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = ParameterHandler(service)
        val fetchCriteria = FetchCriteria(
                accountId = uuid,
                parameterName = "threshold"
        )

        val response = listOf(mockResponse())

        every { RequestParameterHandler.retrieveParameters(serverRequest) } answers { just(fetchCriteria) }
        every { serverRequest.uri().toString() } returns uri
        every { LinkBuilder.buildForParameters(serverRequest.uri().toString(), response) } returns just(response)
        every { service.findByName(any(), any()) } answers { just(response) }

        StepVerifier.create(handler.fetchParameters(serverRequest))
                .assertNext {
                    assertEquals(HttpStatus.OK, it.statusCode())
                }
                .verifyComplete()

        verify(exactly = 1) { service.findByName(eq(fetchCriteria.accountId), eq(fetchCriteria.parameterName!!)) }
        verify(exactly = 1) { LinkBuilder.buildForParameters(eq(uri), eq(response)) }
        verify(exactly = 1) { RequestParameterHandler.retrieveParameters(eq(serverRequest)) }
        verify(exactly = 1) { serverRequest.uri().toString() }
    }

    @Test
    fun testFetchParametersByNameAndReferenceDate() {
        val service = mockk<ParameterService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = ParameterHandler(service)
        val fetchCriteria = FetchCriteria(
                accountId = uuid,
                parameterName = "threshold",
                referenceDate = "202005"
        )

        val response = listOf(mockResponse())

        every { RequestParameterHandler.retrieveParameters(serverRequest) } answers { just(fetchCriteria) }
        every { serverRequest.uri().toString() } returns uri
        every { LinkBuilder.buildForParameters(serverRequest.uri().toString(), response) } returns just(response)
        every { service.findByNameAndReferenceDate(any(), any(), any()) } answers { just(response) }

        StepVerifier.create(handler.fetchParameters(serverRequest))
                .assertNext {
                    assertEquals(HttpStatus.OK, it.statusCode())
                }
                .verifyComplete()

        verify(exactly = 1) { service.findByNameAndReferenceDate(eq(fetchCriteria.accountId),
                eq(fetchCriteria.parameterName!!), eq(fetchCriteria.referenceDate!!)) }
        verify(exactly = 1) { LinkBuilder.buildForParameters(eq(uri), eq(response)) }
        verify(exactly = 1) { RequestParameterHandler.retrieveParameters(eq(serverRequest)) }
        verify(exactly = 1) { serverRequest.uri().toString() }
    }

    @Test
    fun testFetchParametersByNameAndReferenceDateRange() {
        val service = mockk<ParameterService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = ParameterHandler(service)
        val fetchCriteria = FetchCriteria(
                accountId = uuid,
                parameterName = "threshold",
                dateFrom = "202005",
                dateTo = "202006"
        )

        val response = listOf(mockResponse())

        every { RequestParameterHandler.retrieveParameters(serverRequest) } answers { just(fetchCriteria) }
        every { serverRequest.uri().toString() } returns uri
        every { LinkBuilder.buildForParameters(serverRequest.uri().toString(), response) } returns just(response)
        every { service.findByNameAndReferenceDateRange(any(), any(), any(), any()) } answers { just(response) }

        StepVerifier.create(handler.fetchParameters(serverRequest))
                .assertNext {
                    assertEquals(HttpStatus.OK, it.statusCode())
                }
                .verifyComplete()

        verify(exactly = 1) { service.findByNameAndReferenceDateRange(eq(fetchCriteria.accountId),
                eq(fetchCriteria.parameterName!!), eq(fetchCriteria.dateFrom!!), eq(fetchCriteria.dateTo!!)) }
        verify(exactly = 1) { LinkBuilder.buildForParameters(eq(uri), eq(response)) }
        verify(exactly = 1) { RequestParameterHandler.retrieveParameters(eq(serverRequest)) }
        verify(exactly = 1) { serverRequest.uri().toString() }
    }

    @Test
    fun testFetchParametersNoCriteria() {
        val service = mockk<ParameterService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = ParameterHandler(service)
        val fetchCriteria = FetchCriteria(
                accountId = uuid
        )

        every { RequestParameterHandler.retrieveParameters(serverRequest) } answers { just(fetchCriteria) }

        StepVerifier.create(handler.fetchParameters(serverRequest))
                .assertNext {
                    assertEquals(HttpStatus.BAD_REQUEST, it.statusCode())
                }
                .verifyComplete()

        verify(exactly = 1) { RequestParameterHandler.retrieveParameters(eq(serverRequest)) }
        verify(exactly = 0) { serverRequest.uri().toString() }
    }

    @Test
    fun testDeleteById() {
        val service = mockk<ParameterService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = ParameterHandler(service)
        val fetchCriteria = FetchCriteria(accountId = uuid, id = uuid)

        val response = mockResponse()

        every { RequestParameterHandler.retrievePath(serverRequest) } answers { just(fetchCriteria) }

        every { serverRequest.uri().toString() } returns uri
        every { LinkBuilder.buildForParameter(serverRequest.uri().toString(), response) } returns response
        every { service.deleteById(any(), any()) } answers { just(response) }

        StepVerifier.create(handler.deleteById(serverRequest))
                .assertNext {
                    assertEquals(HttpStatus.NO_CONTENT, it.statusCode())
                }
                .verifyComplete()

        verify(exactly = 1) { service.deleteById(eq(fetchCriteria.accountId), eq(fetchCriteria.id!!)) }
        verify(exactly = 0) { LinkBuilder.buildForParameter(eq(uri), eq(response)) }
        verify(exactly = 1) { RequestParameterHandler.retrievePath(eq(serverRequest)) }
        verify(exactly = 0) { serverRequest.uri().toString() }
    }

    private fun mockResponse(): ParameterResponse {
        return ParameterResponse(
                parameterId = uuid,
                name = "threshold",
                value = BigDecimal.TEN.toString(),
                referenceDate = "202005",
                creationDate = LocalDateTime.now()
        )
    }
}