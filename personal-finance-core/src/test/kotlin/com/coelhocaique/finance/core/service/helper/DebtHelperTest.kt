package com.coelhocaique.finance.core.service.helper

import com.coelhocaique.finance.core.domain.dto.DebtRequest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DebtHelperTest {

    @Test
    fun testGenerateDebtsOneInstallment() {
        val debtDate = LocalDate.of(2020, 5, 23)
        val debtDTO = DebtRequest(
                amount = BigDecimal.TEN,
                description = "course",
                installments = 1,
                type = "Credit Card",
                tag = "study",
                debtDate = debtDate,
                nextMonth = false,
                accountId = UUID.randomUUID())

        val debts = DebtHelper.generateDebts(debtDTO)

        assertEquals(1, debts.size)
        assertNotNull(debts[0].debtId)
        assertNotNull(debts[0].creationDate)
        assertNotNull(debts[0].referenceCode)
        assertEquals(debtDTO.amount, debts[0].totalAmount)
        assertEquals(debtDTO.amount, debts[0].amount)
        assertEquals(debtDTO.description, debts[0].description)
        assertEquals(debtDTO.debtDate, debts[0].debtDate)
        assertEquals(debtDTO.installments, debts[0].installments)
        assertEquals(1, debts[0].installmentNumber)
        assertEquals("202005", debts[0].referenceDate)
        assertEquals(debtDTO.tag, debts[0].tag)
        assertEquals(debtDTO.type, debts[0].type)
    }

    @Test
    fun testGenerateDebtsOneInstallmentNextMonth() {
        val debtDate = LocalDate.of(2020, 5, 23)
        val debtDTO = DebtRequest(
                amount = BigDecimal.TEN,
                description = "course",
                installments = 1,
                type = "Credit Card",
                tag = "study",
                debtDate = debtDate,
                nextMonth = true,
                accountId = UUID.randomUUID())

        val debts = DebtHelper.generateDebts(debtDTO)

        assertEquals(1, debts.size)
        assertNotNull(debts[0].debtId)
        assertNotNull(debts[0].creationDate)
        assertNotNull(debts[0].referenceCode)
        assertEquals(debtDTO.amount, debts[0].totalAmount)
        assertEquals(debtDTO.amount, debts[0].amount)
        assertEquals(debtDTO.description, debts[0].description)
        assertEquals(debtDTO.debtDate.plusMonths(1), debts[0].debtDate)
        assertEquals(debtDTO.installments, debts[0].installments)
        assertEquals(1, debts[0].installmentNumber)
        assertEquals("202006", debts[0].referenceDate)
        assertEquals(debtDTO.tag, debts[0].tag)
        assertEquals(debtDTO.type, debts[0].type)
    }

    @Test
    fun testGenerateDebtsMoreThanOneInstallment() {
        val debtDate = LocalDate.of(2020, 5, 23)
        val debtDTO = DebtRequest(
                amount = BigDecimal("100.05"),
                description = "course",
                installments = 2,
                type = "Credit Card",
                tag = "study",
                debtDate = debtDate,
                nextMonth = false,
                accountId = UUID.randomUUID())

        val debts = DebtHelper.generateDebts(debtDTO)

        assertEquals(2, debts.size)
        assertNotNull(debts[0].debtId)
        assertNotNull(debts[0].creationDate)
        assertNotNull(debts[0].referenceCode)
        assertEquals(debtDTO.amount, debts[0].totalAmount)
        assertEquals(BigDecimal("50.03"), debts[0].amount)
        assertEquals(debtDTO.description, debts[0].description)
        assertEquals(debtDTO.debtDate, debts[0].debtDate)
        assertEquals(debtDTO.installments, debts[0].installments)
        assertEquals(1, debts[0].installmentNumber)
        assertEquals("202005", debts[0].referenceDate)
        assertEquals(debtDTO.tag, debts[0].tag)
        assertEquals(debtDTO.type, debts[0].type)

        assertNotNull(debts[1].debtId)
        assertNotNull(debts[1].creationDate)
        assertNotNull(debts[1].referenceCode)
        assertEquals(debtDTO.amount, debts[1].totalAmount)
        assertEquals(BigDecimal("50.02"), debts[1].amount)
        assertEquals(debtDTO.description, debts[1].description)
        assertEquals(debtDTO.debtDate.plusMonths(1), debts[1].debtDate)
        assertEquals(debtDTO.installments, debts[1].installments)
        assertEquals(2, debts[1].installmentNumber)
        assertEquals("202006", debts[1].referenceDate)
        assertEquals(debtDTO.tag, debts[1].tag)
        assertEquals(debtDTO.type, debts[1].type)
    }

    @Test
    fun testGenerateDebtsMoreThanOneInstallmentNextMonth() {
        val debtDate = LocalDate.of(2020, 5, 23)
        val debtDTO = DebtRequest(
                amount = BigDecimal("100.05"),
                description = "course",
                installments = 2,
                type = "Credit Card",
                tag = "study",
                debtDate = debtDate,
                nextMonth = true,
                accountId = UUID.randomUUID())

        val debts = DebtHelper.generateDebts(debtDTO)

        assertEquals(2, debts.size)
        assertNotNull(debts[0].debtId)
        assertNotNull(debts[0].creationDate)
        assertNotNull(debts[0].referenceCode)
        assertEquals(debtDTO.amount, debts[0].totalAmount)
        assertEquals(BigDecimal("50.03"), debts[0].amount)
        assertEquals(debtDTO.description, debts[0].description)
        assertEquals(debtDTO.debtDate.plusMonths(1), debts[0].debtDate)
        assertEquals(debtDTO.installments, debts[0].installments)
        assertEquals(1, debts[0].installmentNumber)
        assertEquals("202006", debts[0].referenceDate)
        assertEquals(debtDTO.tag, debts[0].tag)
        assertEquals(debtDTO.type, debts[0].type)

        assertNotNull(debts[1].debtId)
        assertNotNull(debts[1].creationDate)
        assertNotNull(debts[1].referenceCode)
        assertEquals(debtDTO.amount, debts[1].totalAmount)
        assertEquals(BigDecimal("50.02"), debts[1].amount)
        assertEquals(debtDTO.description, debts[1].description)
        assertEquals(debtDTO.debtDate.plusMonths(2), debts[1].debtDate)
        assertEquals(debtDTO.installments, debts[1].installments)
        assertEquals(2, debts[1].installmentNumber)
        assertEquals("202007", debts[1].referenceDate)
        assertEquals(debtDTO.tag, debts[1].tag)
        assertEquals(debtDTO.type, debts[1].type)
    }
}