package com.coelhocaique.finance.api.dto

import java.util.*

data class CustomAttributeRequestDTO (
        val propertyName: String?,
        val value: String?,
        val accountId: UUID?
)