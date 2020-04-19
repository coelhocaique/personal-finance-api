package com.coelhocaique.finance.api.dto

import com.coelhocaique.finance.core.domain.dto.*
import com.coelhocaique.finance.core.util.generateReferenceDate
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import java.util.*
import java.util.UUID.fromString

object ObjectMapper {

    fun toIncomeDTO(dto: IncomeRequestDTO): Mono<IncomeDTO> =
            just( IncomeDTO(
                    grossAmount = dto.grossAmount!!,
                    description = dto.description!!,
                    accountId = fromString(dto.accountId),
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
                    accountId = fromString(dto.accountId)
            ) )

    fun toRecurringDebtDTO(dto: RecurringDebtRequestDTO): Mono<RecurringDebtDTO> =
            just( RecurringDebtDTO(
                    amount = dto.amount!!,
                    tag = dto.tag!!,
                    type = dto.type!!,
                    description = dto.description!!,
                    accountId = fromString(dto.accountId)
            ) )

    fun toParameterDTO(dto: ParameterRequestDTO): Mono<ParameterDTO> =
            just( ParameterDTO(
                    name = dto.name!!,
                    value = dto.value!!,
                    referenceDate = generateReferenceDate(dto.referenceDate!!),
                    accountId = fromString(dto.accountId)
            ) )


    fun toCustomAttributeDTO(dto: CustomAttributeRequestDTO): Mono<CustomAttributeDTO> =
            just( CustomAttributeDTO(
                    propertyName = dto.propertyName!!,
                    value = dto.value!!,
                    accountId = fromString(dto.accountId)
            ) )
}