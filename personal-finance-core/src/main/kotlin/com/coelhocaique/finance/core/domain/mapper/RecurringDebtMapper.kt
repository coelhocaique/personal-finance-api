package com.coelhocaique.finance.core.domain.mapper

import com.coelhocaique.finance.core.domain.RecurringDebt
import com.coelhocaique.finance.core.domain.dto.DebtRequest
import com.coelhocaique.finance.core.domain.dto.RecurringDebtRequest
import com.coelhocaique.finance.core.domain.dto.RecurringDebtResponse
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

object RecurringDebtMapper {

    fun toDocument(
            dto: RecurringDebtRequest
    ): RecurringDebt = RecurringDebt(
            recurringDebtId = UUID.randomUUID(),
            amount = dto.amount,
            description = dto.description,
            type = dto.type,
            tag = dto.tag,
            accountId = dto.accountId,
            creationDate = LocalDateTime.now()
    )

    fun toDTO(
            document: RecurringDebt
    ): RecurringDebtResponse = RecurringDebtResponse(
            recurringDebtId = document.recurringDebtId,
            amount = document.amount,
            description = document.description,
            type = document.type,
            tag = document.tag,
            creationDate = document.creationDate
    )

    fun toMonoDTO(debt: RecurringDebt): Mono<RecurringDebtResponse> = just(toDTO(debt))

    fun toDebtDTO(
            document: RecurringDebt
    ): DebtRequest = DebtRequest(
            amount = document.amount,
            description = document.description,
            type = document.type,
            tag = document.tag,
            accountId = document.accountId,
            installments = 1,
            debtDate = LocalDate.now(),
            nextMonth = false
    )
}

