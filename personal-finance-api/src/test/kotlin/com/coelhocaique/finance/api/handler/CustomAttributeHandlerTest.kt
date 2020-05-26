package com.coelhocaique.finance.api.handler

import com.coelhocaique.finance.api.dto.CustomAttributeRequestDTO
import com.coelhocaique.finance.api.helper.LinkBuilder
import com.coelhocaique.finance.core.domain.dto.CustomAttributeResponse
import com.coelhocaique.finance.core.service.CustomAttributeService
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
class CustomAttributeHandlerTest {

    private val uuid = UUID.randomUUID()
    private val uri = "uri"

    @BeforeAll
    fun mockStatic() {
        mockkObject(RequestParameterHandler)
        mockkObject(LinkBuilder)
    }

    @Test
    fun testCreate() {
        val service = mockk<CustomAttributeService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = CustomAttributeHandler(service)

        val request = CustomAttributeRequestDTO(
                propertyName = "debt_type",
                value = "food",
                accountId = null)

        val response = mockResponse()

        every { RequestParameterHandler.retrieveAccountId(serverRequest) } answers { just(uuid) }
        every { serverRequest.bodyToMono(CustomAttributeRequestDTO::class.java) } answers { just(request) }
        every { serverRequest.uri().toString() } returns uri
        every { LinkBuilder.buildForCustomAttribute(serverRequest.uri().toString(), response) } returns response
        every { service.create(any()) } answers { just(response) }

        StepVerifier.create(handler.create(serverRequest))
                .assertNext {
                    assertEquals(HttpStatus.CREATED, it.statusCode())
                }
                .verifyComplete()

        verify(exactly = 1) { service.create(any()) }
        verify(exactly = 1) { LinkBuilder.buildForCustomAttribute(eq(uri), eq(response)) }
        verify(exactly = 1) { RequestParameterHandler.retrieveAccountId(eq(serverRequest)) }
        verify(exactly = 1) { serverRequest.bodyToMono(CustomAttributeRequestDTO::class.java) }
        verify(exactly = 1) { serverRequest.uri().toString() }
    }

    @Test
    fun testFetchCustomAttributesByPropertyName() {
        val service = mockk<CustomAttributeService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = CustomAttributeHandler(service)
        val fetchCriteria = FetchCriteria(
                accountId = uuid,
                propertyName = "debt_type"
        )

        val response = listOf(mockResponse())

        every { RequestParameterHandler.retrieveParameters(serverRequest) } answers { just(fetchCriteria) }
        every { serverRequest.uri().toString() } returns uri
        every { LinkBuilder.buildForCustomAttributes(serverRequest.uri().toString(), response) } returns just(response)
        every { service.findByPropertyName(any(), any()) } answers { just(response) }

        StepVerifier.create(handler.fetchCustomAttributes(serverRequest))
                .assertNext {
                    assertEquals(HttpStatus.OK, it.statusCode())
                }
                .verifyComplete()

        verify(exactly = 1) { service.findByPropertyName(eq(fetchCriteria.accountId), eq(fetchCriteria.propertyName!!)) }
        verify(exactly = 1) { LinkBuilder.buildForCustomAttributes(eq(uri), eq(response)) }
        verify(exactly = 1) { RequestParameterHandler.retrieveParameters(eq(serverRequest)) }
        verify(exactly = 1) { serverRequest.uri().toString() }
    }

    @Test
    fun testFetchAllCustomAttributes() {
        val service = mockk<CustomAttributeService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = CustomAttributeHandler(service)
        val fetchCriteria = FetchCriteria(
                accountId = uuid,
                dateFrom = "202005",
                dateTo = "202005"
        )

        val response = listOf(mockResponse())

        every { RequestParameterHandler.retrieveParameters(serverRequest) } answers { just(fetchCriteria) }
        every { serverRequest.uri().toString() } returns uri
        every { LinkBuilder.buildForCustomAttributes(serverRequest.uri().toString(), response) } returns just(response)
        every { service.findAll(any()) } answers { just(response) }

        StepVerifier.create(handler.fetchCustomAttributes(serverRequest))
                .assertNext {
                    assertEquals(HttpStatus.OK, it.statusCode())
                }
                .verifyComplete()

        verify(exactly = 1) { service.findAll(eq(fetchCriteria.accountId)) }
        verify(exactly = 1) { LinkBuilder.buildForCustomAttributes(eq(uri), eq(response)) }
        verify(exactly = 1) { RequestParameterHandler.retrieveParameters(eq(serverRequest)) }
        verify(exactly = 1) { serverRequest.uri().toString() }
    }

    @Test
    fun testDeleteById() {
        val service = mockk<CustomAttributeService>()
        val serverRequest = mockk<ServerRequest>()
        val handler = CustomAttributeHandler(service)
        val fetchCriteria = FetchCriteria(accountId = uuid, id = uuid)

        val response = mockResponse()

        every { RequestParameterHandler.retrievePath(serverRequest) } answers { just(fetchCriteria) }

        every { serverRequest.uri().toString() } returns uri
        every { LinkBuilder.buildForCustomAttribute(serverRequest.uri().toString(), response) } returns response
        every { service.deleteById(any(), any()) } answers { just(response) }

        StepVerifier.create(handler.deleteById(serverRequest))
                .assertNext {
                    assertEquals(HttpStatus.NO_CONTENT, it.statusCode())
                }
                .verifyComplete()

        verify(exactly = 1) { service.deleteById(eq(fetchCriteria.accountId), eq(fetchCriteria.id!!)) }
        verify(exactly = 0) { LinkBuilder.buildForCustomAttribute(eq(uri), eq(response)) }
        verify(exactly = 1) { RequestParameterHandler.retrievePath(eq(serverRequest)) }
        verify(exactly = 0) { serverRequest.uri().toString() }
    }

    private fun mockResponse(): CustomAttributeResponse {
        return CustomAttributeResponse(
                customAttributeId = uuid,
                propertyName = "debt_type",
                value = "food",
                creationDate = LocalDateTime.now()
        )
    }
}