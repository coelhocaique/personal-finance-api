package com.coelhocaique.finance.core.service

import com.coelhocaique.finance.core.domain.RecurringDebt
import com.coelhocaique.finance.core.domain.dto.RecurringDebtRequest
import com.coelhocaique.finance.core.persistance.RecurringDebtRepository
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
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RecurringDebtServiceTest {

    private val accountId = UUID.randomUUID()

    @Test
    fun testCreate() {
        val repository = mockk<RecurringDebtRepository>()
        val service = RecurringDebtService(repository)
        val slot = slot<RecurringDebt>()

        every { repository.insert(capture(slot)) } answers { Mono.just(slot.captured) }

        val request = RecurringDebtRequest(
                accountId = accountId,
                amount = BigDecimal.TEN,
                description = "test",
                type = "Money",
                tag = "testing"
        )

        StepVerifier.create(service.create(Mono.just(request)))
                .assertNext {
                    assertEquals(slot.captured.recurringDebtId, it.recurringDebtId)
                    assertEquals(slot.captured.creationDate, it.creationDate)
                    assertEquals(slot.captured.amount, it.amount)
                    assertEquals(slot.captured.description, it.description)
                    assertEquals(slot.captured.tag, it.tag)
                    assertEquals(slot.captured.type, it.type)
                    assertNull(it.links)
                }
                .verifyComplete()

        verify(exactly = 1) { repository.insert(eq(slot.captured)) }
    }

    @Test
    fun testFindById() {
        val repository = mockk<RecurringDebtRepository>()
        val service = RecurringDebtService(repository)
        val mock = mockDocument()
        val recurringDebtId = mock.recurringDebtId

        every { repository.findById(any(), any()) } answers { Mono.just(mock) }

        StepVerifier.create(service.findById(accountId, recurringDebtId))
                .assertNext {
                    assertEquals(mock.recurringDebtId, it.recurringDebtId)
                    assertEquals(mock.creationDate, it.creationDate)
                    assertEquals(mock.amount, it.amount)
                    assertEquals(mock.amount, it.amount)
                    assertEquals(mock.description, it.description)
                    assertEquals(mock.tag, it.tag)
                    assertEquals(mock.type, it.type)
                    assertNull(it.links)
                }
                .verifyComplete()

        verify(exactly = 1) { repository.findById(eq(recurringDebtId), eq(accountId)) }
    }

    @Test
    fun testFindAll() {
        val repository = mockk<RecurringDebtRepository>()
        val service = RecurringDebtService(repository)
        val mock = mockDocument()

        every { repository.findAll(any()) } answers { Mono.just(listOf(mock)) }

        StepVerifier.create(service.findAll(accountId))
                .assertNext {
                    assertEquals(1, it.size)
                    assertEquals(mock.recurringDebtId, it[0].recurringDebtId)
                    assertEquals(mock.creationDate, it[0].creationDate)
                    assertEquals(mock.amount, it[0].amount)
                    assertEquals(mock.description, it[0].description)
                    assertEquals(mock.tag, it[0].tag)
                    assertEquals(mock.type, it[0].type)
                    assertNull(it[0].links)
                }
                .verifyComplete()

        verify(exactly = 1) { repository.findAll(eq(accountId)) }
    }

    @Test
    fun testDeleteById() {
        val repository = mockk<RecurringDebtRepository>(relaxUnitFun = true)
        val service = RecurringDebtService(repository)
        val mock = mockDocument()
        val recurringDebtId = mock.recurringDebtId

        every { repository.findById(any(), any()) } answers { Mono.just(mock) }

        StepVerifier.create(service.deleteById(accountId, recurringDebtId))
                .assertNext {
                    assertEquals(mock.recurringDebtId, it.recurringDebtId)
                    assertEquals(mock.creationDate, it.creationDate)
                    assertEquals(mock.amount, it.amount)
                    assertEquals(mock.amount, it.amount)
                    assertEquals(mock.description, it.description)
                    assertEquals(mock.tag, it.tag)
                    assertEquals(mock.type, it.type)
                    assertNull(it.links)
                }
                .verifyComplete()

        verify(exactly = 1) { repository.findById(eq(recurringDebtId), eq(accountId)) }
        verify(exactly = 1) { repository.deleteById(eq(recurringDebtId)) }
    }

    private fun mockDocument(): RecurringDebt {
        return RecurringDebt(
                recurringDebtId = UUID.randomUUID(),
                accountId = accountId,
                amount = BigDecimal.TEN,
                description = "test",
                type = "money",
                tag = "testing",
                creationDate = LocalDateTime.now()
        )
    }
}