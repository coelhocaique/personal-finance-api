package com.coelhocaique.finance.core.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

@Document("discount")
data class Discount (
        @Id val id: String? = null,
        val incomeId: String,
        val amount: BigDecimal,
        val description: String,
        val creationDate: LocalDateTime
)