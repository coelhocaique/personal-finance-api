package com.coelhocaique.finance.core.dto

import com.coelhocaique.finance.core.enums.Username
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class DebtDTO (
        val debtId: UUID? = null,
        val amount: BigDecimal,
        val description: String,
        val debtDate: LocalDate,
        val installments: Int,
        val type: String,
        val tag: String,
        val referenceCode: UUID? = null,
        val installmentNumber: Int? = null,
        val referenceDate: String? = null,
        val nextMonth: Boolean? = null,
        val totalAmount: BigDecimal? = null,
        val username: Username = Username.COELHOCAIQUE,
        val creationDate: LocalDateTime? = null
)