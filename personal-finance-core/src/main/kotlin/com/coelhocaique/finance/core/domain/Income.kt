package com.coelhocaique.finance.core.domain

import com.coelhocaique.finance.core.domain.enums.Username
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Document("income")
data class Income (
        @Id val id: String = UUID.randomUUID().toString(),
        val grossAmount: BigDecimal,
        val netAmount: BigDecimal,
        val description: String,
        val receiptDate: LocalDate,
        val referenceDate: String,
        val sourceName: String,
        val username: Username,
        val additionalAmount: BigDecimal? = null,
        val discountAmount: BigDecimal? = null,
        val discounts: List<Discount> = emptyList(),
        val additions: List<Addition> = emptyList(),
        val creationDate: LocalDateTime = LocalDateTime.now()
)