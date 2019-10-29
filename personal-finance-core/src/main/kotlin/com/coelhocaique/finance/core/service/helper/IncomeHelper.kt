package com.coelhocaique.finance.core.service.helper

import com.coelhocaique.finance.core.domain.dto.AdditionDTO
import com.coelhocaique.finance.core.domain.dto.DiscountDTO
import com.coelhocaique.finance.core.domain.dto.IncomeDTO
import java.math.BigDecimal

object IncomeHelper {

    fun calculateIncome(incomeDTO: IncomeDTO): IncomeDTO {
        val discountAmount = calculateDiscountAmount(incomeDTO.discounts)
        val additionalAmount = calculateAdditionalAmount(incomeDTO.additions)
        val netAmount = calculateNetAmount(incomeDTO.grossAmount, additionalAmount!!, discountAmount!!)
        return incomeDTO.copy(netAmount = netAmount,
                additionalAmount = additionalAmount,
                discountAmount = discountAmount)
    }

    private fun calculateDiscountAmount(discounts: List<DiscountDTO>?) =
            discounts?.fold(BigDecimal.ZERO) { x, it -> x.add(it.amount) }

    private fun calculateAdditionalAmount(additions: List<AdditionDTO>?) =
            additions?.fold(BigDecimal.ZERO) { x, it -> x.add(it.amount) }

    private fun calculateNetAmount(grossAmount: BigDecimal, additionalAmount: BigDecimal,
                                   discountAmount: BigDecimal): BigDecimal =
        grossAmount.add(additionalAmount).subtract(discountAmount)

}