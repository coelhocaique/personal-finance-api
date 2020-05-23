package com.coelhocaique.finance.core.service.helper

import com.coelhocaique.finance.core.domain.dto.AdditionDTO
import com.coelhocaique.finance.core.domain.dto.DiscountDTO
import com.coelhocaique.finance.core.domain.dto.IncomeDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.math.BigDecimal
import java.time.LocalDate

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IncomeHelperTest{

    @Test
    fun testCalculateIncomeNoAdditionNoDiscount() {

        val incomeDTO = IncomeDTO(
                grossAmount = BigDecimal.TEN,
                description = "salary",
                receiptDate = LocalDate.now(),
                referenceDate = "202005",
                sourceName = "income source"
        )

        val calculatedIncome = IncomeHelper.calculateIncome(incomeDTO)

        assertEquals(incomeDTO.grossAmount, calculatedIncome.grossAmount)
        assertEquals(incomeDTO.grossAmount, calculatedIncome.netAmount)
        assertEquals(BigDecimal.ZERO, calculatedIncome.additionalAmount)
        assertEquals(BigDecimal.ZERO, calculatedIncome.discountAmount)
    }

    @Test
    fun testCalculateIncomeWithAdditionNoDiscount() {
        val additionalAmount = BigDecimal.TEN

        val incomeDTO = IncomeDTO(
                grossAmount = BigDecimal.TEN,
                description = "salary",
                receiptDate = LocalDate.now(),
                referenceDate = "202005",
                sourceName = "income source",
                additions = listOf(AdditionDTO(additionalAmount, "addition"))
        )

        val calculatedIncome = IncomeHelper.calculateIncome(incomeDTO)

        assertEquals(incomeDTO.grossAmount, calculatedIncome.grossAmount)
        assertEquals(incomeDTO.grossAmount.add(additionalAmount), calculatedIncome.netAmount)
        assertEquals(additionalAmount, calculatedIncome.additionalAmount)
        assertEquals(BigDecimal.ZERO, calculatedIncome.discountAmount)
    }

    @Test
    fun testCalculateIncomeNoAdditionWithDiscount() {
        val discountAmount = BigDecimal.TEN

        val incomeDTO = IncomeDTO(
                grossAmount = BigDecimal.TEN,
                description = "salary",
                receiptDate = LocalDate.now(),
                referenceDate = "202005",
                sourceName = "income source",
                discounts = listOf(DiscountDTO(discountAmount, "discount"))
        )

        val calculatedIncome = IncomeHelper.calculateIncome(incomeDTO)

        assertEquals(incomeDTO.grossAmount, calculatedIncome.grossAmount)
        assertEquals(incomeDTO.grossAmount.subtract(discountAmount), calculatedIncome.netAmount)
        assertEquals(BigDecimal.ZERO, calculatedIncome.additionalAmount)
        assertEquals(discountAmount, calculatedIncome.discountAmount)
    }

    @Test
    fun testCalculateIncomeWithAdditionWithDiscount() {
        val additionalAmount = BigDecimal.TEN
        val discountAmount = BigDecimal.ONE

        val incomeDTO = IncomeDTO(
                grossAmount = BigDecimal.TEN,
                description = "salary",
                receiptDate = LocalDate.now(),
                referenceDate = "202005",
                sourceName = "income source",
                discounts = listOf(DiscountDTO(discountAmount, "discount")),
                additions = listOf(AdditionDTO(additionalAmount, "addition"))
        )

        val calculatedIncome = IncomeHelper.calculateIncome(incomeDTO)

        assertEquals(incomeDTO.grossAmount, calculatedIncome.grossAmount)
        assertEquals(incomeDTO.grossAmount.add(additionalAmount).subtract(discountAmount), calculatedIncome.netAmount)
        assertEquals(additionalAmount, calculatedIncome.additionalAmount)
        assertEquals(discountAmount, calculatedIncome.discountAmount)
    }
}