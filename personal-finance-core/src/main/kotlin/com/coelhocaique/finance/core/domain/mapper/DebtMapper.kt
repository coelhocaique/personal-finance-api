package com.coelhocaique.finance.core.domain.mapper

import com.coelhocaique.finance.core.domain.Debt
import com.coelhocaique.finance.core.domain.dto.DebtDTO
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import java.util.*
import java.util.stream.Collectors

object DebtMapper {

    fun toDocument(dto: DebtDTO): Debt =
            Debt(amount = dto.amount,
                    description = dto.description,
                    debtDate = dto.debtDate,
                    referenceCode = dto.referenceCode!!.toString(),
                    installmentNumber = dto.installmentNumber!!,
                    referenceDate = dto.referenceDate!!,
                    type = dto.type,
                    tag = dto.tag,
                    installments = dto.installments,
                    totalAmount = dto.totalAmount!!,
                    username = dto.username)

    fun toDTO(debt: Debt): DebtDTO =
            DebtDTO(
                    debtId = UUID.fromString(debt.id),
                    amount = debt.amount,
                    description = debt.description,
                    debtDate = debt.debtDate,
                    referenceCode = UUID.fromString(debt.referenceCode),
                    installmentNumber = debt.installmentNumber,
                    referenceDate = debt.referenceDate,
                    type = debt.type,
                    tag = debt.tag,
                    installments = debt.installments,
                    totalAmount = debt.totalAmount,
                    username = debt.username,
                    creationDate = debt.creationDate)

    fun toMonoDTO(debt: Debt): Mono<DebtDTO> = just(toDTO(debt))

}

