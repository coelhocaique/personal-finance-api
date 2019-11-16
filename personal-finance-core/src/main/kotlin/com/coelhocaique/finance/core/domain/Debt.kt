package com.coelhocaique.finance.core.domain

import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

data class Debt (
        val debtId: UUID,
        val amount: BigDecimal,
        val description: String,
        val debtDate: LocalDate,
        val referenceCode: UUID,
        val installmentNumber: Int,
        val referenceDate: String,
        val type: String,
        val tag: String,
        val installments: Int,
        val totalAmount: BigDecimal,
        val accountId: UUID,
        val creationDate: LocalDateTime
)