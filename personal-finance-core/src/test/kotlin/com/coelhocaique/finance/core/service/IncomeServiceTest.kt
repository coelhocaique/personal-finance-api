package com.coelhocaique.finance.core.service

import com.coelhocaique.finance.core.domain.Income
import com.coelhocaique.finance.core.domain.dto.IncomeRequest
import com.coelhocaique.finance.core.persistance.IncomeRepository
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
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IncomeServiceTest {

    private val accountId = UUID.randomUUID()

    @Test
    fun testCreate() {
        val repository = mockk<IncomeRepository>()
        val service = IncomeService(repository)
        val date = LocalDate.of(2020, 5, 23)
        val slot = slot<Income>()

        every { repository.insert(capture(slot)) } answers { Mono.just(slot.captured) }

        val request = IncomeRequest(
                accountId = accountId,
                grossAmount = BigDecimal.TEN,
                referenceDate = date,
                sourceName = "income source",
                description = "test",
                receiptDate = LocalDate.now(),
                discounts = emptyList(),
                additions = emptyList()
        )

        StepVerifier.create(service.create(Mono.just(request)))
                .assertNext {
                    assertEquals(slot.captured.accountId, accountId)
                    assertEquals(slot.captured.incomeId, it.incomeId)
                    assertEquals(slot.captured.grossAmount, it.grossAmount)
                    assertEquals(slot.captured.netAmount, it.netAmount)
                    assertEquals(slot.captured.additionalAmount, it.additionalAmount)
                    assertEquals(slot.captured.discountAmount, it.discountAmount)
                    assertEquals(slot.captured.description, it.description)
                    assertEquals(slot.captured.referenceDate, it.referenceDate)
                    assertEquals(slot.captured.sourceName, it.sourceName)
                    assertEquals(slot.captured.receiptDate, it.receiptDate)
                    assertEquals(slot.captured.creationDate, it.creationDate)
                    assertEquals(slot.captured.discounts.size, it.discounts.size)
                    assertEquals(slot.captured.additions.size, it.additions.size)
                    assertNull(it.links)
                }
                .verifyComplete()

        verify(exactly = 1) { repository.insert(eq(slot.captured)) }
    }

    @Test
    fun testFindById() {
        val repository = mockk<IncomeRepository>()
        val service = IncomeService(repository)
        val mock = mockDocument()
        val incomeId = mock.incomeId

        every { repository.findById(any(), any()) } answers { Mono.just(mock) }

        StepVerifier.create(service.findById(accountId, incomeId))
                .assertNext {
                    assertEquals(mock.incomeId, it.incomeId)
                    assertEquals(mock.grossAmount, it.grossAmount)
                    assertEquals(mock.netAmount, it.netAmount)
                    assertEquals(mock.additionalAmount, it.additionalAmount)
                    assertEquals(mock.discountAmount, it.discountAmount)
                    assertEquals(mock.description, it.description)
                    assertEquals(mock.referenceDate, it.referenceDate)
                    assertEquals(mock.sourceName, it.sourceName)
                    assertEquals(mock.receiptDate, it.receiptDate)
                    assertEquals(mock.creationDate, it.creationDate)
                    assertEquals(mock.discounts.size, it.discounts.size)
                    assertEquals(mock.additions.size, it.additions.size)
                    assertNull(it.links)
                }
                .verifyComplete()

        verify(exactly = 1) { repository.findById(eq(incomeId), eq(accountId)) }
    }

    @Test
    fun testFindByReferenceDate() {
        val repository = mockk<IncomeRepository>()
        val service = IncomeService(repository)
        val mock = mockDocument()
        val referenceDate = mock.referenceDate

        every { repository.findByReferenceDate(any(), any()) } answers { Mono.just(listOf(mock)) }

        StepVerifier.create(service.findByReferenceDate(accountId, referenceDate))
                .assertNext {
                    assertEquals(1, it.size)
                    assertEquals(mock.incomeId, it[0].incomeId)
                    assertEquals(mock.grossAmount, it[0].grossAmount)
                    assertEquals(mock.netAmount, it[0].netAmount)
                    assertEquals(mock.additionalAmount, it[0].additionalAmount)
                    assertEquals(mock.discountAmount, it[0].discountAmount)
                    assertEquals(mock.description, it[0].description)
                    assertEquals(mock.referenceDate, it[0].referenceDate)
                    assertEquals(mock.sourceName, it[0].sourceName)
                    assertEquals(mock.receiptDate, it[0].receiptDate)
                    assertEquals(mock.creationDate, it[0].creationDate)
                    assertEquals(mock.discounts.size, it[0].discounts.size)
                    assertEquals(mock.additions.size, it[0].additions.size)
                    assertNull(it[0].links)
                }
                .verifyComplete()

        verify(exactly = 1) { repository.findByReferenceDate(eq(referenceDate), eq(accountId)) }
    }

    @Test
    fun testFindByReferenceDateRange() {
        val repository = mockk<IncomeRepository>()
        val service = IncomeService(repository)
        val mock = mockDocument()
        val referenceDate = mock.referenceDate

        every { repository.findByReferenceDateBetween(any(), any(), any()) } answers { Mono.just(listOf(mock)) }

        StepVerifier.create(service.findByReferenceDateRange(accountId, referenceDate, referenceDate))
                .assertNext {
                    assertEquals(1, it.size)
                    assertEquals(mock.incomeId, it[0].incomeId)
                    assertEquals(mock.grossAmount, it[0].grossAmount)
                    assertEquals(mock.netAmount, it[0].netAmount)
                    assertEquals(mock.additionalAmount, it[0].additionalAmount)
                    assertEquals(mock.discountAmount, it[0].discountAmount)
                    assertEquals(mock.description, it[0].description)
                    assertEquals(mock.referenceDate, it[0].referenceDate)
                    assertEquals(mock.sourceName, it[0].sourceName)
                    assertEquals(mock.receiptDate, it[0].receiptDate)
                    assertEquals(mock.creationDate, it[0].creationDate)
                    assertEquals(mock.discounts.size, it[0].discounts.size)
                    assertEquals(mock.additions.size, it[0].additions.size)
                    assertNull(it[0].links)
                }
                .verifyComplete()

        verify(exactly = 1) { repository.findByReferenceDateBetween(eq(referenceDate),
                eq(referenceDate), eq(accountId)) }
    }

    @Test
    fun testDeleteById() {
        val repository = mockk<IncomeRepository>(relaxUnitFun = true)
        val service = IncomeService(repository)
        val mock = mockDocument()
        val incomeId = mock.incomeId

        every { repository.findById(any(), any()) } answers { Mono.just(mock) }

        StepVerifier.create(service.deleteById(accountId, incomeId))
                .assertNext {
                    assertEquals(mock.incomeId, it.incomeId)
                    assertEquals(mock.grossAmount, it.grossAmount)
                    assertEquals(mock.netAmount, it.netAmount)
                    assertEquals(mock.additionalAmount, it.additionalAmount)
                    assertEquals(mock.discountAmount, it.discountAmount)
                    assertEquals(mock.description, it.description)
                    assertEquals(mock.referenceDate, it.referenceDate)
                    assertEquals(mock.sourceName, it.sourceName)
                    assertEquals(mock.receiptDate, it.receiptDate)
                    assertEquals(mock.creationDate, it.creationDate)
                    assertEquals(mock.discounts.size, it.discounts.size)
                    assertEquals(mock.additions.size, it.additions.size)
                    assertNull(it.links)
                }
                .verifyComplete()

        verify(exactly = 1) { repository.findById(eq(incomeId), eq(accountId)) }
        verify(exactly = 1) { repository.deleteById(eq(incomeId)) }
    }

    private fun mockDocument(): Income {
        return Income(
                incomeId = UUID.randomUUID(),
                accountId = accountId,
                grossAmount = BigDecimal.TEN,
                netAmount = BigDecimal.TEN,
                referenceDate = "202005",
                sourceName = "income source",
                description = "test",
                receiptDate = LocalDate.now(),
                additionalAmount = BigDecimal.ZERO,
                discountAmount = BigDecimal.ZERO,
                discounts = emptyList(),
                additions = emptyList(),
                creationDate = LocalDateTime.now()
        )
    }
}