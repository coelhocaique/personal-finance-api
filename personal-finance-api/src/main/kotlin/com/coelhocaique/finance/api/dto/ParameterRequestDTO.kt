package com.coelhocaique.finance.api.dto

import java.time.LocalDate

data class ParameterRequestDTO (
        val name: String,
        val value: String,
        val referenceDate: LocalDate,
        val userId: String = "44df25d4-08a4-4e3e-9a3b-8b1e39483380"
)