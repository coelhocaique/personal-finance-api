package com.coelhocaique.finance.api.dto

import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

data class IncomeResponseDTO (
        val id: String,
        val description: String,
        val date: LocalDate,
        val referenceMonth: Int,
        val referenceYear: Int,
        val companyName: String,
        val owner: String,
        val discountAmount: BigDecimal,
        val extraAmount: BigDecimal,
        val grossAmount: BigDecimal,
        val netAmount: BigDecimal,
        val creationDate: LocalDateTime
)