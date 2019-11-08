package com.coelhocaique.finance.core.domain.mapper

import com.coelhocaique.finance.core.domain.Debt
import com.coelhocaique.finance.core.domain.dto.DebtDTO
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import java.time.LocalDateTime
import java.util.*

object DebtMapper {

    fun toDocument(dto: DebtDTO): Debt =
            Debt(id = UUID.randomUUID().toString(),
                    amount = dto.amount.toString(),
                    description = dto.description,
                    debtDate = dto.debtDate,
                    referenceCode = dto.referenceCode!!.toString(),
                    installmentNumber = dto.installmentNumber!!.toString(),
                    referenceDate = dto.referenceDate!!,
                    type = dto.type,
                    tag = dto.tag,
                    installments = dto.installments.toString(),
                    totalAmount = dto.totalAmount!!.toString(),
                    accountId = dto.accountId,
                    creationDate = LocalDateTime.now())

    fun toDTO(debt: Debt): DebtDTO =
            DebtDTO(debtId = UUID.fromString(debt.id),
                    amount = debt.amount?.toBigDecimal()!!,
                    description = debt.description!!,
                    debtDate = debt.debtDate!!,
                    referenceCode = UUID.fromString(debt.referenceCode),
                    installmentNumber = debt.installmentNumber?.toInt()!!,
                    referenceDate = debt.referenceDate,
                    type = debt.type!!,
                    tag = debt.tag!!,
                    installments = debt.installments?.toInt()!!,
                    totalAmount = debt.totalAmount?.toBigDecimal()!!,
                    creationDate = debt.creationDate)

    fun toMonoDTO(debt: Debt): Mono<DebtDTO> = just(toDTO(debt))

}

