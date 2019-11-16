package com.coelhocaique.finance.core.domain

import java.time.LocalDateTime
import java.util.*

data class CustomAttribute (
        val customAttributeId: UUID,
        val propertyName: String,
        val value: String,
        val accountId: UUID,
        val creationDate: LocalDateTime
)