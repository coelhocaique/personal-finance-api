package com.coelhocaique.finance.api.dto

import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

data class DebtRequestDTO (
        val amount: BigDecimal?,
        val description: String?,
        val debtDate: LocalDate?,
        val type: String?,
        val tag: String?,
        val installments: Int? = 1,
        val nextMonth: Boolean = false,
        val accountId: UUID?
)