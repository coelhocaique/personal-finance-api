package com.coelhocaique.finance.core.service

import com.coelhocaique.finance.core.domain.Debt
import com.coelhocaique.finance.core.domain.dto.DebtRequest
import com.coelhocaique.finance.core.persistance.DebtRepository
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
class DebtServiceTest {

    private val accountId = UUID.randomUUID()

    @Test
    fun testCreate() {
        val repository = mockk<DebtRepository>()
        val service = DebtService(repository)
        val date = LocalDate.of(2020, 5, 23)
        val slot = slot<List<Debt>>()

        every { repository.insertAll(capture(slot)) } answers { Mono.just(slot.captured) }

        val request = DebtRequest(
                accountId = accountId,
                amount = BigDecimal.TEN,
                debtDate = date,
                installments = 1,
                description = "test",
                type = "Money",
                tag = "testing"
        )

        StepVerifier.create(service.create(Mono.just(request)))
                .assertNext {
                    assertEquals(1, it.size)
                    assertEquals(slot.captured[0].debtId, it[0].debtId)
                    assertEquals(slot.captured[0].creationDate, it[0].creationDate)
                    assertEquals(slot.captured[0].referenceCode, it[0].referenceCode)
                    assertEquals(slot.captured[0].amount, it[0].totalAmount)
                    assertEquals(slot.captured[0].amount, it[0].amount)
                    assertEquals(slot.captured[0].description, it[0].description)
                    assertEquals(slot.captured[0].debtDate, it[0].debtDate)
                    assertEquals(slot.captured[0].installments, it[0].installments)
                    assertEquals(1, it[0].installmentNumber)
                    assertEquals("202005", it[0].referenceDate)
                    assertEquals(slot.captured[0].tag, it[0].tag)
                    assertEquals(slot.captured[0].type, it[0].type)
                    assertNull(it[0].links)
                }
                .verifyComplete()

        verify(exactly = 1) { repository.insertAll(eq(slot.captured)) }
    }

    @Test
    fun testFindById() {
        val repository = mockk<DebtRepository>()
        val service = DebtService(repository)
        val mock = mockDocument()[0]
        val debtId = mock.debtId

        every { repository.findById(any(), any()) } answers { Mono.just(mock) }

        StepVerifier.create(service.findById(accountId, debtId))
                .assertNext {
                    assertEquals(mock.debtId, it.debtId)
                    assertEquals(mock.creationDate, it.creationDate)
                    assertEquals(mock.referenceCode, it.referenceCode)
                    assertEquals(mock.amount, it.totalAmount)
                    assertEquals(mock.amount, it.amount)
                    assertEquals(mock.description, it.description)
                    assertEquals(mock.debtDate, it.debtDate)
                    assertEquals(mock.installments, it.installments)
                    assertEquals(1, it.installmentNumber)
                    assertEquals("202005", it.referenceDate)
                    assertEquals(mock.tag, it.tag)
                    assertEquals(mock.type, it.type)
                    assertNull(it.links)
                }
                .verifyComplete()

        verify(exactly = 1) { repository.findById(eq(debtId), eq(accountId)) }
    }

    @Test
    fun testFindByReferenceDate() {
        val repository = mockk<DebtRepository>()
        val service = DebtService(repository)
        val mock = mockDocument()
        val referenceDate = mock[0].referenceDate

        every { repository.findByReferenceDate(any(), any()) } answers { Mono.just(mock) }

        StepVerifier.create(service.findByReferenceDate(accountId, referenceDate))
                .assertNext {
                    assertEquals(1, it.size)
                    assertEquals(mock[0].debtId, it[0].debtId)
                    assertEquals(mock[0].creationDate, it[0].creationDate)
                    assertEquals(mock[0].referenceCode, it[0].referenceCode)
                    assertEquals(mock[0].amount, it[0].totalAmount)
                    assertEquals(mock[0].amount, it[0].amount)
                    assertEquals(mock[0].description, it[0].description)
                    assertEquals(mock[0].debtDate, it[0].debtDate)
                    assertEquals(mock[0].installments, it[0].installments)
                    assertEquals(1, it[0].installmentNumber)
                    assertEquals("202005", it[0].referenceDate)
                    assertEquals(mock[0].tag, it[0].tag)
                    assertEquals(mock[0].type, it[0].type)
                    assertNull(it[0].links)
                }
                .verifyComplete()

        verify(exactly = 1) { repository.findByReferenceDate(eq(referenceDate), eq(accountId)) }
    }

    @Test
    fun testFindByReferenceCode() {
        val repository = mockk<DebtRepository>()
        val service = DebtService(repository)
        val mock = mockDocument()
        val referenceCode = mock[0].referenceCode

        every { repository.findByReferenceCode(any(), any()) } answers { Mono.just(mock) }

        StepVerifier.create(service.findByReferenceCode(accountId, referenceCode))
                .assertNext {
                    assertEquals(1, it.size)
                    assertEquals(mock[0].debtId, it[0].debtId)
                    assertEquals(mock[0].creationDate, it[0].creationDate)
                    assertEquals(mock[0].referenceCode, it[0].referenceCode)
                    assertEquals(mock[0].amount, it[0].totalAmount)
                    assertEquals(mock[0].amount, it[0].amount)
                    assertEquals(mock[0].description, it[0].description)
                    assertEquals(mock[0].debtDate, it[0].debtDate)
                    assertEquals(mock[0].installments, it[0].installments)
                    assertEquals(1, it[0].installmentNumber)
                    assertEquals("202005", it[0].referenceDate)
                    assertEquals(mock[0].tag, it[0].tag)
                    assertEquals(mock[0].type, it[0].type)
                    assertNull(it[0].links)
                }
                .verifyComplete()

        verify(exactly = 1) { repository.findByReferenceCode(eq(referenceCode), eq(accountId)) }
    }

    @Test
    fun testFindByReferenceDateRange() {
        val repository = mockk<DebtRepository>()
        val service = DebtService(repository)
        val mock = mockDocument()
        val referenceDate = mock[0].referenceDate

        every { repository.findByReferenceDateBetween(any(), any(), any()) } answers { Mono.just(mock) }

        StepVerifier.create(service.findByReferenceDateRange(accountId, referenceDate, referenceDate))
                .assertNext {
                    assertEquals(1, it.size)
                    assertEquals(mock[0].debtId, it[0].debtId)
                    assertEquals(mock[0].creationDate, it[0].creationDate)
                    assertEquals(mock[0].referenceCode, it[0].referenceCode)
                    assertEquals(mock[0].amount, it[0].totalAmount)
                    assertEquals(mock[0].amount, it[0].amount)
                    assertEquals(mock[0].description, it[0].description)
                    assertEquals(mock[0].debtDate, it[0].debtDate)
                    assertEquals(mock[0].installments, it[0].installments)
                    assertEquals(1, it[0].installmentNumber)
                    assertEquals("202005", it[0].referenceDate)
                    assertEquals(mock[0].tag, it[0].tag)
                    assertEquals(mock[0].type, it[0].type)
                    assertNull(it[0].links)
                }
                .verifyComplete()

        verify(exactly = 1) { repository.findByReferenceDateBetween(eq(referenceDate),
                eq(referenceDate), eq(accountId)) }
    }

    @Test
    fun testDeleteById() {
        val repository = mockk<DebtRepository>(relaxUnitFun = true)
        val service = DebtService(repository)
        val mock = mockDocument()[0]
        val debtId = mock.debtId

        every { repository.findById(any(), any()) } answers { Mono.just(mock) }

        StepVerifier.create(service.deleteById(accountId, debtId))
                .assertNext {
                    assertEquals(mock.debtId, it.debtId)
                    assertEquals(mock.creationDate, it.creationDate)
                    assertEquals(mock.referenceCode, it.referenceCode)
                    assertEquals(mock.amount, it.totalAmount)
                    assertEquals(mock.amount, it.amount)
                    assertEquals(mock.description, it.description)
                    assertEquals(mock.debtDate, it.debtDate)
                    assertEquals(mock.installments, it.installments)
                    assertEquals(1, it.installmentNumber)
                    assertEquals("202005", it.referenceDate)
                    assertEquals(mock.tag, it.tag)
                    assertEquals(mock.type, it.type)
                    assertNull(it.links)
                }
                .verifyComplete()

        verify(exactly = 1) { repository.findById(eq(debtId), eq(accountId)) }
        verify(exactly = 1) { repository.deleteById(eq(debtId)) }
    }

    @Test
    fun testDeleteByReferenceCode() {
        val repository = mockk<DebtRepository>(relaxUnitFun = true)
        val service = DebtService(repository)
        val mock = mockDocument()
        val referenceCode = mock[0].referenceCode
        val debtId = mock[0].debtId

        every { repository.findByReferenceCode(any(), any()) } answers { Mono.just(mock) }

        StepVerifier.create(service.deleteByReferenceCode(accountId, referenceCode))
                .assertNext {
                    assertEquals(1, it.size)
                    assertEquals(mock[0].debtId, it[0].debtId)
                    assertEquals(mock[0].creationDate, it[0].creationDate)
                    assertEquals(mock[0].referenceCode, it[0].referenceCode)
                    assertEquals(mock[0].amount, it[0].totalAmount)
                    assertEquals(mock[0].amount, it[0].amount)
                    assertEquals(mock[0].description, it[0].description)
                    assertEquals(mock[0].debtDate, it[0].debtDate)
                    assertEquals(mock[0].installments, it[0].installments)
                    assertEquals(1, it[0].installmentNumber)
                    assertEquals("202005", it[0].referenceDate)
                    assertEquals(mock[0].tag, it[0].tag)
                    assertEquals(mock[0].type, it[0].type)
                    assertNull(it[0].links)
                }
                .verifyComplete()

        verify(exactly = 1) { repository.findByReferenceCode(eq(referenceCode), eq(accountId)) }
        verify(exactly = 1) { repository.deleteById(eq(debtId)) }
    }

    private fun mockDocument(): List<Debt> {
        return listOf(Debt(
                debtId = UUID.randomUUID(),
                accountId = accountId,
                amount = BigDecimal.TEN,
                referenceDate = "202005",
                referenceCode = UUID.randomUUID(),
                debtDate = LocalDate.of(2020, 5, 23),
                description = "test",
                type = "money",
                tag = "testing",
                installments = 1,
                installmentNumber = 1,
                totalAmount = BigDecimal.TEN,
                creationDate = LocalDateTime.now())
        )
    }
}