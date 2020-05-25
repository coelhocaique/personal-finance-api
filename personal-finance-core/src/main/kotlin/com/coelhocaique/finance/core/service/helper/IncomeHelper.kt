package com.coelhocaique.finance.core.service.helper

import com.coelhocaique.finance.core.domain.Income
import com.coelhocaique.finance.core.domain.dto.AdditionDTO
import com.coelhocaique.finance.core.domain.dto.DiscountDTO
import com.coelhocaique.finance.core.domain.dto.IncomeRequest
import com.coelhocaique.finance.core.domain.mapper.IncomeMapper
import com.coelhocaique.finance.core.util.generateReferenceDate
import java.math.BigDecimal

object IncomeHelper {

    fun generateIncome(
            request: IncomeRequest
    ): Income {
        val discountAmount = calculateDiscountAmount(request.discounts)
        val additionalAmount = calculateAdditionalAmount(request.additions)
        val netAmount = calculateNetAmount(request.grossAmount, additionalAmount, discountAmount)
        val referenceDate = generateReferenceDate(request.referenceDate)
        return IncomeMapper.toDocument(request, netAmount, additionalAmount, discountAmount, referenceDate)
    }

    private fun calculateDiscountAmount(
            discounts: List<DiscountDTO>
    ) = discounts.fold(BigDecimal.ZERO) { x, it -> x.add(it.amount) }

    private fun calculateAdditionalAmount(
            additions: List<AdditionDTO>
    ) = additions.fold(BigDecimal.ZERO) { x, it -> x.add(it.amount) }

    private fun calculateNetAmount(
            grossAmount: BigDecimal,
            additionalAmount: BigDecimal,
            discountAmount: BigDecimal
    ): BigDecimal = grossAmount.add(additionalAmount).subtract(discountAmount)
}