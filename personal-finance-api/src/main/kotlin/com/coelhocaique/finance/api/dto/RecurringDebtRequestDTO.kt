package com.coelhocaique.finance.api.dto

import java.math.BigDecimal
import java.util.*

data class RecurringDebtRequestDTO (
        val amount: BigDecimal?,
        val description: String?,
        val type: String?,
        val tag: String?,
        val accountId: UUID?
)