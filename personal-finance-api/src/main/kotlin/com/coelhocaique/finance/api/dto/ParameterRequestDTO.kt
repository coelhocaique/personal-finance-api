package com.coelhocaique.finance.api.dto

import java.time.LocalDate

data class ParameterRequestDTO (
        val name: String?,
        val value: String?,
        val referenceDate: LocalDate?,
        val accountId: String?
)