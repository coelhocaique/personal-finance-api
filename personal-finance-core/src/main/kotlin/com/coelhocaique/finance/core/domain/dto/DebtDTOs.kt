package com.coelhocaique.finance.core.domain.dto

import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

data class DebtRequest (
        val amount: BigDecimal,
        val description: String,
        val debtDate: LocalDate,
        val installments: Int,
        val type: String,
        val tag: String,
        val accountId: UUID,
        val nextMonth: Boolean = false
)

data class DebtResponse (
        val debtId: UUID,
        val amount: BigDecimal,
        val description: String,
        val debtDate: LocalDate,
        val installments: Int,
        val type: String,
        val tag: String,
        val referenceCode: UUID,
        val installmentNumber: Int,
        val referenceDate: String,
        val totalAmount: BigDecimal,
        val creationDate: LocalDateTime,
        val links: List<Map<String, String>>? = null
)