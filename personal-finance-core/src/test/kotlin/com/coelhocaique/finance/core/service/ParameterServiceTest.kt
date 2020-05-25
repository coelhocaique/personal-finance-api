package com.coelhocaique.finance.core.service

import com.coelhocaique.finance.core.domain.Parameter
import com.coelhocaique.finance.core.domain.dto.ParameterRequest
import com.coelhocaique.finance.core.persistance.ParameterRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ParameterServiceTest {

    private val accountId = UUID.randomUUID()

    @Test
    fun testCreate() {
        val repository = mockk<ParameterRepository>()
        val service = ParameterService(repository)
        val date = LocalDate.of(2020, 5, 23)
        val slot = slot<Parameter>()

        every { repository.insert(capture(slot)) } answers { Mono.just(slot.captured) }

        val request = ParameterRequest(
                name = "threshold",
                value = "100",
                referenceDate = date,
                accountId = accountId
        )

        StepVerifier.create(service.create(Mono.just(request)))
                .assertNext {
                    assertEquals(slot.captured.accountId, accountId)
                    assertEquals(slot.captured.parameterId, it.parameterId)
                    assertEquals(slot.captured.parameterName, it.name)
                    assertEquals(slot.captured.parameterValue, it.value)
                    assertEquals(slot.captured.referenceDate, it.referenceDate)
                    assertEquals(slot.captured.creationDate, it.creationDate)
                    assertNull(it.links)
                }
                .verifyComplete()

        verify(exactly = 1) { repository.insert(eq(slot.captured)) }
    }

    @Test
    fun testFindById() {
        val repository = mockk<ParameterRepository>()
        val service = ParameterService(repository)
        val mock = mockDocument()
        val parameterId = mock.parameterId

        every { repository.findById(any(), any()) } answers { Mono.just(mock) }

        StepVerifier.create(service.findById(accountId, parameterId))
                .assertNext {
                    assertEquals(mock.parameterId, it.parameterId)
                    assertEquals(mock.parameterName, it.name)
                    assertEquals(mock.parameterValue, it.value)
                    assertEquals(mock.referenceDate, it.referenceDate)
                    assertEquals(mock.creationDate, it.creationDate)
                    assertNull(it.links)
                }
                .verifyComplete()

        verify(exactly = 1) { repository.findById(eq(parameterId), eq(accountId)) }
    }

    @Test
    fun testFindByReferenceDate() {
        val repository = mockk<ParameterRepository>()
        val service = ParameterService(repository)
        val mock = mockDocument()
        val referenceDate = mock.referenceDate

        every { repository.findByReferenceDate(any(), any()) } answers { Mono.just(listOf(mock)) }

        StepVerifier.create(service.findByReferenceDate(accountId, referenceDate))
                .assertNext {
                    assertEquals(1, it.size)
                    assertEquals(mock.parameterId, it[0].parameterId)
                    assertEquals(mock.parameterName, it[0].name)
                    assertEquals(mock.parameterValue, it[0].value)
                    assertEquals(mock.referenceDate, it[0].referenceDate)
                    assertEquals(mock.creationDate, it[0].creationDate)
                    assertNull(it[0].links)
                }
                .verifyComplete()

        verify(exactly = 1) { repository.findByReferenceDate(eq(referenceDate), eq(accountId)) }
    }

    @Test
    fun testFindByNameAndReferenceDate() {
        val repository = mockk<ParameterRepository>()
        val service = ParameterService(repository)
        val mock = mockDocument()
        val referenceDate = mock.referenceDate
        val name = mock.parameterName

        every { repository.findByNameAndReferenceDate(any(), any(), any()) } answers { Mono.just(listOf(mock)) }

        StepVerifier.create(service.findByNameAndReferenceDate(accountId, name, referenceDate))
                .assertNext {
                    assertEquals(1, it.size)
                    assertEquals(mock.parameterId, it[0].parameterId)
                    assertEquals(mock.parameterName, it[0].name)
                    assertEquals(mock.parameterValue, it[0].value)
                    assertEquals(mock.referenceDate, it[0].referenceDate)
                    assertEquals(mock.creationDate, it[0].creationDate)
                    assertNull(it[0].links)
                }
                .verifyComplete()

        verify(exactly = 1) { repository.findByNameAndReferenceDate(eq(name), eq(referenceDate), eq(accountId)) }
    }

    @Test
    fun testFindByNameAndReferenceDateRange() {
        val repository = mockk<ParameterRepository>()
        val service = ParameterService(repository)
        val mock = mockDocument()
        val referenceDate = mock.referenceDate
        val name = mock.parameterName

        every { repository.findByNameAndReferenceDateBetween(any(), any(), any(), any()) } answers { Mono.just(listOf(mock)) }

        StepVerifier.create(service.findByNameAndReferenceDateRange(accountId, name, referenceDate, referenceDate))
                .assertNext {
                    assertEquals(1, it.size)
                    assertEquals(mock.parameterId, it[0].parameterId)
                    assertEquals(mock.parameterName, it[0].name)
                    assertEquals(mock.parameterValue, it[0].value)
                    assertEquals(mock.referenceDate, it[0].referenceDate)
                    assertEquals(mock.creationDate, it[0].creationDate)
                    assertNull(it[0].links)
                }
                .verifyComplete()

        verify(exactly = 1) { repository.findByNameAndReferenceDateBetween(eq(name), eq(referenceDate),
                eq(referenceDate), eq(accountId)) }
    }

    @Test
    fun testFindByName() {
        val repository = mockk<ParameterRepository>()
        val service = ParameterService(repository)
        val mock = mockDocument()
        val name = mock.parameterName

        every { repository.findByName(any(), any()) } answers { Mono.just(listOf(mock)) }

        StepVerifier.create(service.findByName(accountId, name))
                .assertNext {
                    assertEquals(1, it.size)
                    assertEquals(mock.parameterId, it[0].parameterId)
                    assertEquals(mock.parameterName, it[0].name)
                    assertEquals(mock.parameterValue, it[0].value)
                    assertEquals(mock.referenceDate, it[0].referenceDate)
                    assertEquals(mock.creationDate, it[0].creationDate)
                    assertNull(it[0].links)
                }
                .verifyComplete()

        verify(exactly = 1) { repository.findByName(eq(name),  eq(accountId)) }
    }

    @Test
    fun testFindByReferenceDateRange() {
        val repository = mockk<ParameterRepository>()
        val service = ParameterService(repository)
        val mock = mockDocument()
        val referenceDate = mock.referenceDate

        every { repository.findByReferenceDateBetween(any(), any(), any()) } answers { Mono.just(listOf(mock)) }

        StepVerifier.create(service.findByReferenceDateRange(accountId, referenceDate, referenceDate))
                .assertNext {
                    assertEquals(1, it.size)
                    assertEquals(mock.parameterId, it[0].parameterId)
                    assertEquals(mock.parameterName, it[0].name)
                    assertEquals(mock.parameterValue, it[0].value)
                    assertEquals(mock.referenceDate, it[0].referenceDate)
                    assertEquals(mock.creationDate, it[0].creationDate)
                    assertNull(it[0].links)
                }
                .verifyComplete()

        verify(exactly = 1) { repository.findByReferenceDateBetween(eq(referenceDate),
                eq(referenceDate), eq(accountId)) }
    }

    @Test
    fun testDeleteById() {
        val repository = mockk<ParameterRepository>(relaxUnitFun = true)
        val service = ParameterService(repository)
        val mock = mockDocument()
        val parameterId = mock.parameterId

        every { repository.findById(any(), any()) } answers { Mono.just(mock) }

        StepVerifier.create(service.deleteById(accountId, parameterId))
                .assertNext {
                    assertEquals(mock.parameterId, it.parameterId)
                    assertEquals(mock.parameterName, it.name)
                    assertEquals(mock.parameterValue, it.value)
                    assertEquals(mock.referenceDate, it.referenceDate)
                    assertEquals(mock.creationDate, it.creationDate)
                    assertNull(it.links)
                }
                .verifyComplete()

        verify(exactly = 1) { repository.findById(eq(parameterId), eq(accountId)) }
        verify(exactly = 1) { repository.deleteById(eq(parameterId)) }
    }

    private fun mockDocument(): Parameter {
        return Parameter(
                parameterId = UUID.randomUUID(),
                accountId = accountId,
                parameterName = "threshold",
                parameterValue = "100",
                referenceDate = "202005",
                creationDate = LocalDateTime.now()
        )
    }
}