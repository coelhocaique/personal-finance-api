package com.coelhocaique.finance.core.mapper

import com.coelhocaique.finance.core.document.Debt
import com.coelhocaique.finance.core.dto.DebtDTO
import com.coelhocaique.finance.core.enums.Username
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*
import java.util.stream.Collectors

object DebtMapper {

    fun toDocument(dto: DebtDTO): Debt =
            Debt(amount = dto.amount,
                    description = dto.description,
                    debtDate = dto.debtDate,
                    referenceCode = dto.referenceCode!!,
                    installmentNumber = dto.installmentNumber!!,
                    referenceDate = dto.referenceDate!!,
                    type = dto.type,
                    tag = dto.tag,
                    installments = dto.installments,
                    totalAmount = dto.totalAmount!!,
                    username = dto.username)

    fun toDTO(debts: List<Debt>): List<DebtDTO> =
            debts.stream().map (::toDTO).collect(Collectors.toUnmodifiableList())

    fun toDTO(debt: Debt): DebtDTO =
            DebtDTO(
                    debtId = UUID.fromString(debt.id),
                    amount = debt.amount,
                    description = debt.description,
                    debtDate = debt.debtDate,
                    referenceCode = debt.referenceCode,
                    installmentNumber = debt.installmentNumber,
                    referenceDate = debt.referenceDate,
                    type = debt.type,
                    tag = debt.tag,
                    installments = debt.installments,
                    totalAmount = debt.totalAmount,
                    username = debt.username,
                    creationDate = debt.creationDate)

}

