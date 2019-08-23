package com.coelhocaique.finance.core.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

@Document("income")
data class Income (
        @Id val id: String? = null,
        val grossAmount: BigDecimal,
        val netAmount: BigDecimal,
        val discountAmount: BigDecimal,
        val extraAmount: BigDecimal,
        val description: String,
        val date: LocalDate,
        val referenceMonth: Int,
        val referenceYear: Int,
        val owner: String,
        val companyName: String,
        val creationDate: LocalDateTime
)