package com.coelhocaique.finance.core.domain.mapper

import com.coelhocaique.finance.core.domain.Debt
import com.coelhocaique.finance.core.domain.dto.DebtRequest
import com.coelhocaique.finance.core.domain.dto.DebtResponse
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

object DebtMapper {

    fun toDocument(
            dto: DebtRequest,
            referenceCode: UUID,
            installmentNumber: Int,
            referenceDate: String,
            totalAmount: BigDecimal
    ): Debt = Debt(
            debtId = UUID.randomUUID(),
            amount = dto.amount,
            description = dto.description,
            debtDate = dto.debtDate,
            type = dto.type,
            tag = dto.tag,
            installments = dto.installments,
            accountId = dto.accountId,
            referenceCode = referenceCode,
            installmentNumber = installmentNumber,
            referenceDate = referenceDate,
            totalAmount = totalAmount,
            creationDate = LocalDateTime.now()
    )

    fun toDTO(
            document: Debt
    ): DebtResponse = DebtResponse(
            debtId = document.debtId,
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
            creationDate = document.creationDate
    )

    fun toMonoDTO(debt: Debt): Mono<DebtResponse> = just(toDTO(debt))

}

