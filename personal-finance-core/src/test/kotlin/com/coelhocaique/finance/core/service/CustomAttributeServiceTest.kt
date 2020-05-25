package com.coelhocaique.finance.core.service

import com.coelhocaique.finance.core.domain.CustomAttribute
import com.coelhocaique.finance.core.domain.dto.CustomAttributeRequest
import com.coelhocaique.finance.core.persistance.CustomAttributeRepository
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
import java.time.LocalDateTime
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomAttributeServiceTest {

    private val accountId = UUID.randomUUID()

    @Test
    fun testCreate() {
        val repository = mockk<CustomAttributeRepository>()
        val service = CustomAttributeService(repository)
        val slot = slot<CustomAttribute>()

        every { repository.insert(capture(slot)) } answers { Mono.just(slot.captured) }

        val request = CustomAttributeRequest(
                accountId = accountId,
                propertyName = "debt_type",
                value = "housing"
        )

        StepVerifier.create(service.create(Mono.just(request)))
                .assertNext {
                    assertEquals(slot.captured.customAttributeId, it.customAttributeId)
                    assertEquals(slot.captured.creationDate, it.creationDate)
                    assertEquals(slot.captured.propertyName, it.propertyName)
                    assertEquals(slot.captured.value, it.value)
                    assertNull(it.links)
                }
                .verifyComplete()

        verify(exactly = 1) { repository.insert(eq(slot.captured)) }
    }

    @Test
    fun testFindByPropertyName() {
        val repository = mockk<CustomAttributeRepository>()
        val service = CustomAttributeService(repository)
        val mock = mockDocument()
        val propertyName = mock.propertyName

        every { repository.findByPropertyName(any(), any()) } answers { Mono.just(listOf(mock)) }

        StepVerifier.create(service.findByPropertyName(accountId, propertyName))
                .assertNext {
                    assertEquals(1, it.size)
                    assertEquals(mock.customAttributeId, it[0].customAttributeId)
                    assertEquals(mock.creationDate, it[0].creationDate)
                    assertEquals(mock.propertyName, it[0].propertyName)
                    assertEquals(mock.value, it[0].value)
                    assertNull(it[0].links)
                }
                .verifyComplete()

        verify(exactly = 1) { repository.findByPropertyName(eq(propertyName), eq(accountId)) }
    }

    @Test
    fun testFindAll() {
        val repository = mockk<CustomAttributeRepository>()
        val service = CustomAttributeService(repository)
        val mock = mockDocument()

        every { repository.findAll(any()) } answers { Mono.just(listOf(mock)) }

        StepVerifier.create(service.findAll(accountId))
                .assertNext {
                    assertEquals(1, it.size)
                    assertEquals(mock.customAttributeId, it[0].customAttributeId)
                    assertEquals(mock.creationDate, it[0].creationDate)
                    assertEquals(mock.propertyName, it[0].propertyName)
                    assertEquals(mock.value, it[0].value)
                    assertNull(it[0].links)
                }
                .verifyComplete()

        verify(exactly = 1) { repository.findAll(eq(accountId)) }
    }

    @Test
    fun testDeleteById() {
        val repository = mockk<CustomAttributeRepository>(relaxUnitFun = true)
        val service = CustomAttributeService(repository)
        val mock = mockDocument()
        val customAttributeId = mock.customAttributeId

        every { repository.findById(any(), any()) } answers { Mono.just(mock) }

        StepVerifier.create(service.deleteById(accountId, customAttributeId))
                .assertNext {
                    assertEquals(mock.customAttributeId, it.customAttributeId)
                    assertEquals(mock.creationDate, it.creationDate)
                    assertEquals(mock.propertyName, it.propertyName)
                    assertEquals(mock.value, it.value)
                    assertNull(it.links)
                }
                .verifyComplete()

        verify(exactly = 1) { repository.findById(eq(customAttributeId), eq(accountId)) }
        verify(exactly = 1) { repository.deleteById(eq(customAttributeId)) }
    }

    private fun mockDocument(): CustomAttribute {
        return CustomAttribute(
                customAttributeId = UUID.randomUUID(),
                accountId = accountId,
                propertyName = "debt_type",
                value = "housing",
                creationDate = LocalDateTime.now()
        )
    }
}