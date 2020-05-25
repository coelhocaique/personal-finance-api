package com.coelhocaique.finance.core.service.helper

import com.coelhocaique.finance.core.domain.dto.AdditionDTO
import com.coelhocaique.finance.core.domain.dto.DiscountDTO
import com.coelhocaique.finance.core.domain.dto.IncomeRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IncomeHelperTest{

    private val accountId = UUID.randomUUID()

    @Test
    fun testCalculateIncomeNoAdditionNoDiscount() {

        val request = IncomeRequest(
                accountId = accountId,
                grossAmount = BigDecimal.TEN,
                description = "salary",
                receiptDate = LocalDate.now(),
                referenceDate = LocalDate.of(2020, 5, 24),
                sourceName = "income source"
        )

        val income = IncomeHelper.generateIncome(request)

        assertEquals(request.grossAmount, income.grossAmount)
        assertEquals(request.grossAmount, income.netAmount)
        assertEquals(BigDecimal.ZERO, income.additionalAmount)
        assertEquals(BigDecimal.ZERO, income.discountAmount)
        assertEquals("202005", income.referenceDate)
    }

    @Test
    fun testCalculateIncomeWithAdditionNoDiscount() {
        val additionalAmount = BigDecimal.TEN

        val request = IncomeRequest(
                accountId = accountId,
                grossAmount = BigDecimal.TEN,
                description = "salary",
                receiptDate = LocalDate.now(),
                referenceDate = LocalDate.of(2020, 5, 24),
                sourceName = "income source",
                additions = listOf(AdditionDTO(additionalAmount, "addition"))
        )

        val income = IncomeHelper.generateIncome(request)

        assertEquals(request.grossAmount, income.grossAmount)
        assertEquals(request.grossAmount.add(additionalAmount), income.netAmount)
        assertEquals(additionalAmount, income.additionalAmount)
        assertEquals(BigDecimal.ZERO, income.discountAmount)
        assertEquals("202005", income.referenceDate)
    }

    @Test
    fun testCalculateIncomeNoAdditionWithDiscount() {
        val discountAmount = BigDecimal.TEN

        val request = IncomeRequest(
                accountId = accountId,
                grossAmount = BigDecimal.TEN,
                description = "salary",
                receiptDate = LocalDate.now(),
                referenceDate = LocalDate.of(2020, 5, 24),
                sourceName = "income source",
                discounts = listOf(DiscountDTO(discountAmount, "discount"))
        )

        val income = IncomeHelper.generateIncome(request)

        assertEquals(request.grossAmount, income.grossAmount)
        assertEquals(request.grossAmount.subtract(discountAmount), income.netAmount)
        assertEquals(BigDecimal.ZERO, income.additionalAmount)
        assertEquals(discountAmount, income.discountAmount)
        assertEquals("202005", income.referenceDate)
    }

    @Test
    fun testCalculateIncomeWithAdditionWithDiscount() {
        val additionalAmount = BigDecimal.TEN
        val discountAmount = BigDecimal.ONE

        val request = IncomeRequest(
                accountId = accountId,
                grossAmount = BigDecimal.TEN,
                description = "salary",
                receiptDate = LocalDate.now(),
                referenceDate = LocalDate.of(2020, 5, 24),
                sourceName = "income source",
                discounts = listOf(DiscountDTO(discountAmount, "discount")),
                additions = listOf(AdditionDTO(additionalAmount, "addition"))
        )

        val income = IncomeHelper.generateIncome(request)

        assertEquals(request.grossAmount, income.grossAmount)
        assertEquals(request.grossAmount.add(additionalAmount).subtract(discountAmount), income.netAmount)
        assertEquals(additionalAmount, income.additionalAmount)
        assertEquals(discountAmount, income.discountAmount)
        assertEquals("202005", income.referenceDate)
    }
}