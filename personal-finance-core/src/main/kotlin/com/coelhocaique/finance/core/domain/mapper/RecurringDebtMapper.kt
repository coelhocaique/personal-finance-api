package com.coelhocaique.finance.core.domain.mapper

import com.coelhocaique.finance.core.domain.RecurringDebt
import com.coelhocaique.finance.core.domain.dto.DebtDTO
import com.coelhocaique.finance.core.domain.dto.RecurringDebtDTO
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

object RecurringDebtMapper {

    fun toDocument(dto: RecurringDebtDTO): RecurringDebt =
            RecurringDebt(recurringDebtId = UUID.randomUUID(),
                    amount = dto.amount,
                    description = dto.description,
                    type = dto.type,
                    tag = dto.tag,
                    accountId = dto.accountId!!,
                    creationDate = LocalDateTime.now())

    fun toDTO(document: RecurringDebt): RecurringDebtDTO =
            RecurringDebtDTO(
                    recurringDebtId = document.recurringDebtId,
                    amount = document.amount,
                    description = document.description,
                    type = document.type,
                    tag = document.tag,
                    creationDate = document.creationDate)

    fun toMonoDTO(debt: RecurringDebt): Mono<RecurringDebtDTO> = just(toDTO(debt))

    fun toDebtDTO(document: RecurringDebt): DebtDTO =
            DebtDTO(amount = document.amount,
                    description = document.description,
                    type = document.type,
                    tag = document.tag,
                    accountId = document.accountId,
                    creationDate = document.creationDate,
                    installments = 1,
                    debtDate = LocalDate.now(),
                    nextMonth = false)

}

