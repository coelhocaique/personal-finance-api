package com.coelhocaique.finance.core.domain.mapper

import com.coelhocaique.finance.core.domain.Debt
import com.coelhocaique.finance.core.domain.dto.DebtDTO
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import java.time.LocalDateTime
import java.util.*

object DebtMapper {

    fun toDocument(dto: DebtDTO): Debt =
            Debt(debtId = UUID.randomUUID(),
                    amount = dto.amount,
                    description = dto.description,
                    debtDate = dto.debtDate,
                    referenceCode = dto.referenceCode!!,
                    installmentNumber = dto.installmentNumber!!,
                    referenceDate = dto.referenceDate!!,
                    type = dto.type,
                    tag = dto.tag,
                    installments = dto.installments,
                    totalAmount = dto.totalAmount!!,
                    accountId = dto.accountId!!,
                    creationDate = LocalDateTime.now())

    fun toDTO(document: Debt): DebtDTO =
            DebtDTO(debtId = document.debtId,
                    amount = document.amount,
                    description = document.description,
                    debtDate = document.debtDate,
                    referenceCode = document.referenceCode,
                    installmentNumber = document.installmentNumber,
                    referenceDate = document.referenceDate,
                    type = document.type,
                    tag = document.tag,
                    installments = document.installments,
                    totalAmount = document.totalAmount,
                    creationDate = document.creationDate)

    fun toMonoDTO(debt: Debt): Mono<DebtDTO> = just(toDTO(debt))

}

