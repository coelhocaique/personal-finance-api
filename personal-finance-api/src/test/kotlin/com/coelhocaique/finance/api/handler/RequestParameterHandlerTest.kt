package com.coelhocaique.finance.api.handler

import com.coelhocaique.finance.api.handler.RequestParameterHandler.extractBody
import com.coelhocaique.finance.api.handler.RequestParameterHandler.retrieveAccountId
import com.coelhocaique.finance.api.handler.RequestParameterHandler.retrieveParameters
import com.coelhocaique.finance.api.handler.RequestParameterHandler.retrievePath
import com.coelhocaique.finance.api.helper.Fields.AUTHORIZATION
import com.coelhocaique.finance.api.helper.Fields.DATE_FROM
import com.coelhocaique.finance.api.helper.Fields.DATE_TO
import com.coelhocaique.finance.api.helper.Fields.ID
import com.coelhocaique.finance.api.helper.Fields.NAME
import com.coelhocaique.finance.api.helper.Fields.PROPERTY_NAME
import com.coelhocaique.finance.api.helper.Fields.REF_CODE
import com.coelhocaique.finance.api.helper.Fields.REF_DATE
import com.coelhocaique.finance.api.helper.Messages.INVALID_ID
import com.coelhocaique.finance.api.helper.Messages.INVALID_REF_CODE
import com.coelhocaique.finance.api.helper.Messages.INVALID_REQUEST
import com.coelhocaique.finance.api.helper.Messages.MISSING_HEADERS
import com.coelhocaique.finance.api.helper.exception.ApiException
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.web.reactive.function.server.ServerRequest
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RequestParameterHandlerTest {

    private val aValidUuid = UUID.randomUUID()
    private val aValidUuidString = aValidUuid.toString()
    private val aInvalidUuid = "invalid uuid"
    private val referenceDate = "202005"

    @Test
    fun testRetrieveAllParametersSuccessfully() {
        val serverRequest = mockk<ServerRequest>()

        every { serverRequest.headers().header(AUTHORIZATION) } returns listOf(aValidUuidString)
        every { serverRequest.queryParam(REF_CODE) } returns Optional.of(aValidUuidString)
        every { serverRequest.queryParam(REF_DATE) } returns Optional.of(referenceDate)
        every { serverRequest.queryParam(DATE_FROM) } returns Optional.of(referenceDate)
        every { serverRequest.queryParam(DATE_TO) } returns Optional.of(referenceDate)
        every { serverRequest.queryParam(NAME) } returns Optional.of(NAME)
        every { serverRequest.queryParam(PROPERTY_NAME) } returns Optional.of(PROPERTY_NAME)

        StepVerifier.create(retrieveParameters(serverRequest))
                .assertNext {
                    assertEquals(aValidUuid, it.accountId)
                    assertEquals(aValidUuid, it.referenceCode)
                    assertEquals(referenceDate, it.referenceDate)
                    assertEquals(referenceDate, it.dateFrom)
                    assertEquals(referenceDate, it.dateTo)
                    assertEquals(NAME, it.parameterName)
                    assertEquals(PROPERTY_NAME, it.propertyName)
                    assertNull(it.id)
                }
                .verifyComplete()
    }

    @Test
    fun testRetrieveParametersUnauthorized() {
        val serverRequest = mockk<ServerRequest>()

        every { serverRequest.headers().header(AUTHORIZATION) } returns listOf()

        StepVerifier.create(retrieveParameters(serverRequest))
                .expectErrorSatisfies {
                    it as ApiException
                    assertEquals(ApiException.ExceptionType.UNAUTHORIZED_EXCEPTION, it.type)
                    assertEquals(listOf(MISSING_HEADERS), it.messages)
                }
                .verify()
    }

    @Test
    fun testRetrieveParametersInvalidAccountId() {
        val serverRequest = mockk<ServerRequest>()

        every { serverRequest.headers().header(AUTHORIZATION) } returns listOf(aInvalidUuid)

        StepVerifier.create(retrieveParameters(serverRequest))
                .expectErrorSatisfies {
                    it as ApiException
                    assertEquals(ApiException.ExceptionType.UNAUTHORIZED_EXCEPTION, it.type)
                    assertEquals(listOf(MISSING_HEADERS), it.messages)
                }
                .verify()
    }

    @Test
    fun testRetrieveParametersInvalidReferenceCode() {
        val serverRequest = mockk<ServerRequest>()

        every { serverRequest.headers().header(AUTHORIZATION) } returns listOf(aValidUuidString)
        every { serverRequest.queryParam(REF_CODE) } returns Optional.of(aInvalidUuid)

        StepVerifier.create(retrieveParameters(serverRequest))
                .expectErrorSatisfies {
                    it as ApiException
                    assertEquals(ApiException.ExceptionType.BUSINESS_EXCEPTION, it.type)
                    assertEquals(listOf(INVALID_REF_CODE), it.messages)
                }
                .verify()
    }

    @Test
    fun testRetrieveParametersAllOptionalParametersNotPresent() {
        val serverRequest = mockk<ServerRequest>()

        every { serverRequest.headers().header(AUTHORIZATION) } returns listOf(aValidUuidString)
        every { serverRequest.queryParam(REF_CODE) } returns Optional.empty()
        every { serverRequest.queryParam(REF_DATE) } returns Optional.empty()
        every { serverRequest.queryParam(DATE_FROM) } returns Optional.empty()
        every { serverRequest.queryParam(DATE_TO) } returns Optional.empty()
        every { serverRequest.queryParam(NAME) } returns Optional.empty()
        every { serverRequest.queryParam(PROPERTY_NAME) } returns Optional.empty()

        StepVerifier.create(retrieveParameters(serverRequest))
                .assertNext {
                    assertEquals(aValidUuid, it.accountId)
                    assertNull(it.referenceCode)
                    assertNull(it.referenceDate)
                    assertNull(it.dateFrom)
                    assertNull(it.dateTo)
                    assertNull(it.parameterName)
                    assertNull(it.propertyName)
                    assertNull(it.id)
                }
                .verifyComplete()
    }

    @Test
    fun testRetrievePathSuccessfully() {
        val serverRequest = mockk<ServerRequest>()

        every { serverRequest.headers().header(AUTHORIZATION) } returns listOf(aValidUuidString)
        every { serverRequest.pathVariable(ID) } returns aValidUuidString

        StepVerifier.create(retrievePath(serverRequest))
                .assertNext {
                    assertEquals(aValidUuid, it.accountId)
                    assertEquals(aValidUuid, it.id)
                }
                .verifyComplete()
    }

    @Test
    fun testRetrievePathUnauthorized() {
        val serverRequest = mockk<ServerRequest>()

        every { serverRequest.headers().header(AUTHORIZATION) } returns listOf(aInvalidUuid)

        StepVerifier.create(retrievePath(serverRequest))
                .expectErrorSatisfies {
                    it as ApiException
                    assertEquals(ApiException.ExceptionType.UNAUTHORIZED_EXCEPTION, it.type)
                    assertEquals(listOf(MISSING_HEADERS), it.messages)
                }
                .verify()
    }

    @Test
    fun testRetrievePathInvalidId() {
        val serverRequest = mockk<ServerRequest>()

        every { serverRequest.headers().header(AUTHORIZATION) } returns listOf(aValidUuidString)
        every { serverRequest.pathVariable(ID) } returns aInvalidUuid

        StepVerifier.create(retrievePath(serverRequest))
                .expectErrorSatisfies {
                    it as ApiException
                    assertEquals(ApiException.ExceptionType.BUSINESS_EXCEPTION, it.type)
                    assertEquals(listOf(INVALID_ID), it.messages)
                }
                .verify()
    }

    @Test
    fun testRetrieveAccountIdSuccessfully() {
        val serverRequest = mockk<ServerRequest>()

        every { serverRequest.headers().header(AUTHORIZATION) } returns listOf(aValidUuidString)

        StepVerifier.create(retrieveAccountId(serverRequest))
                .assertNext {
                    assertEquals(aValidUuid, it)
                }
                .verifyComplete()
    }

    @Test
    fun testRetrieveAccountIdEmptyAuthorization() {
        val serverRequest = mockk<ServerRequest>()

        every { serverRequest.headers().header(AUTHORIZATION) } returns listOf()

        StepVerifier.create(retrieveAccountId(serverRequest))
                .expectErrorSatisfies {
                    it as ApiException
                    assertEquals(ApiException.ExceptionType.UNAUTHORIZED_EXCEPTION, it.type)
                    assertEquals(listOf(MISSING_HEADERS), it.messages)
                }
                .verify()
    }

    @Test
    fun testRetrieveAccountIdInvalidAuthorization() {
        val serverRequest = mockk<ServerRequest>()

        every { serverRequest.headers().header(AUTHORIZATION) } returns listOf(aInvalidUuid)

        StepVerifier.create(retrieveAccountId(serverRequest))
                .expectErrorSatisfies {
                    it as ApiException
                    assertEquals(ApiException.ExceptionType.UNAUTHORIZED_EXCEPTION, it.type)
                    assertEquals(listOf(MISSING_HEADERS), it.messages)
                }
                .verify()
    }

    @Test
    fun testExtractBodySuccessfully() {
        val serverRequest = mockk<ServerRequest>()
        val body = "test"

        every { serverRequest.bodyToMono(String::class.java) } returns Mono.just(body)

        StepVerifier.create(extractBody<String>(serverRequest))
                .assertNext {
                    assertEquals(body, it)
                }
                .verifyComplete()
    }

    @Test
    fun testExtractBodyInvalidRequest() {
        val serverRequest = mockk<ServerRequest>()

        every { serverRequest.bodyToMono(String::class.java) } returns Mono.error(RuntimeException())

        StepVerifier.create(extractBody<String>(serverRequest))
                .expectErrorSatisfies {
                    it as ApiException
                    assertEquals(ApiException.ExceptionType.BUSINESS_EXCEPTION, it.type)
                    assertEquals(listOf(INVALID_REQUEST), it.messages)
                }
                .verify()
    }
}