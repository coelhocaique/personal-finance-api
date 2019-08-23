package com.coelhocaique.finance.api.dto

import com.coelhocaique.finance.core.enums.Owner
import java.math.BigDecimal
import java.time.LocalDate
import javax.validation.Valid
import javax.validation.constraints.NotNull

data class IncomeRequestDTO (
        @NotNull val amount: BigDecimal? = null,
        @NotNull val description: String? = null,
        @NotNull val date: LocalDate? = null,
        @NotNull val referenceMonth: Int? = null,
        @NotNull val referenceYear: Int? = null,
        @NotNull val companyName: String? = null,
        val owner: String = Owner.CAIQUE.toString(),
        @Valid val discounts: List<DiscountRequestDTO> = emptyList(),
        @Valid val additions: List<AdditionRequestDTO> = emptyList()
){
    fun totalDiscount(): BigDecimal{
        return discounts.fold(BigDecimal.ZERO){x, it -> x.add(it.amount)}
    }

    fun totalExtraAmount(): BigDecimal{
        return additions.fold(BigDecimal.ZERO) { x, it -> x.add(it.amount) }
    }

    fun grossAmount(): BigDecimal{
        return amount!!.add(totalExtraAmount())
    }

    fun netAmount(): BigDecimal{
        return grossAmount().subtract(totalDiscount())
    }
}