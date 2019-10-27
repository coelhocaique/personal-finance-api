package com.coelhocaique.finance.core.domain

import com.coelhocaique.finance.core.domain.enums.Username
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Document("debt")
data class Debt (
        @Id val id: String = UUID.randomUUID().toString(),
        val amount: BigDecimal,
        val description: String,
        val debtDate: LocalDate,
        val referenceCode: String,
        val installmentNumber: Int,
        val referenceDate: String,
        val type: String,
        val tag: String,
        val installments: Int,
        val totalAmount: BigDecimal,
        val username: Username,
        val creationDate: LocalDateTime = LocalDateTime.now()
)