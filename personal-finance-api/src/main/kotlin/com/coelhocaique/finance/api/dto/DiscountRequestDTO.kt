package com.coelhocaique.finance.api.dto

import java.math.BigDecimal
import javax.validation.constraints.NotNull

data class DiscountRequestDTO (
        @NotNull val amount: BigDecimal? = null,
        @NotNull val description: String? = null
)