package com.coelhocaique.finance.api.dto

import java.math.BigDecimal
import java.time.LocalDate

data class DebtRequestDTO (
        val amount: BigDecimal?,
        val description: String?,
        val debtDate: LocalDate?,
        val installments: Int?,
        val type: String?,
        val tag: String?,
        val nextMonth: Boolean = false,
        val userId: String = "44df25d4-08a4-4e3e-9a3b-8b1e39483380"
)