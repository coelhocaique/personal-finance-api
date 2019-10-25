package com.coelhocaique.finance.api.dto

import com.coelhocaique.finance.core.enums.Username
import java.math.BigDecimal
import java.time.LocalDate

data class DebtRequestDTO (
        val amount: BigDecimal? = null,
        val description: String? = null,
        val debtDate: LocalDate? = null,
        val installments: Int? = null,
        val type: String? = null,
        val tag: String? = null,
        val nextMonth: Boolean = false,
        val username: Username = Username.COELHOCAIQUE
)