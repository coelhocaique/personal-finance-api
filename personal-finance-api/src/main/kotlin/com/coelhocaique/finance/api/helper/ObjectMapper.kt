package com.coelhocaique.finance.api.helper

import com.coelhocaique.finance.api.dto.DebtRequestDTO
import com.coelhocaique.finance.api.dto.IncomeRequestDTO
import com.coelhocaique.finance.core.dto.DebtDTO
import com.coelhocaique.finance.core.dto.IncomeDTO
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just

object ObjectMapper {

    fun toIncomeDTO(dto: IncomeRequestDTO): Mono<IncomeDTO> =
            just( IncomeDTO(
                    grossAmount = dto.grossAmount!!,
                    description = dto.description!!,
                    username = dto.username,
                    referenceDate = dto.referenceDate!!,
                    receiptDate = dto.receiptDate!!,
                    sourceName = dto.sourceName!!,
                    discounts = dto.discounts,
                    additions = dto.additions
            ) )

    fun toDebtDTO(dto: DebtRequestDTO): Mono<DebtDTO> =
            just( DebtDTO(
                    amount = dto.amount!!,
                    debtDate = dto.debtDate!!,
                    tag = dto.tag!!,
                    type = dto.type!!,
                    installments = dto.installments!!,
                    nextMonth = dto.nextMonth,
                    description = dto.description!!,
                    username = dto.username
            ) )
}