package com.coelhocaique.finance.api.dto

import com.coelhocaique.finance.core.domain.dto.*
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import java.util.UUID.fromString

object ObjectMapper {

    fun toIncomeDTO(dto: IncomeRequestDTO): Mono<IncomeRequest> =
            just( IncomeRequest(
                    grossAmount = dto.grossAmount!!,
                    description = dto.description!!,
                    accountId = dto.accountId!!,
                    referenceDate = dto.referenceDate!!,
                    receiptDate = dto.receiptDate!!,
                    sourceName = dto.sourceName!!,
                    discounts = dto.discounts,
                    additions = dto.additions
            ) )

    fun toDebtDTO(dto: DebtRequestDTO): Mono<DebtRequest> =
            just( DebtRequest(
                    amount = dto.amount!!,
                    debtDate = dto.debtDate!!,
                    tag = dto.tag!!,
                    type = dto.type!!,
                    installments = dto.installments!!,
                    nextMonth = dto.nextMonth,
                    description = dto.description!!,
                    accountId = dto.accountId!!
            ) )

    fun toRecurringDebtDTO(dto: RecurringDebtRequestDTO): Mono<RecurringDebtRequest> =
            just( RecurringDebtRequest(
                    amount = dto.amount!!,
                    tag = dto.tag!!,
                    type = dto.type!!,
                    description = dto.description!!,
                    accountId = dto.accountId!!
            ) )

    fun toParameterDTO(dto: ParameterRequestDTO): Mono<ParameterRequest> =
            just( ParameterRequest(
                    name = dto.name!!,
                    value = dto.value!!,
                    referenceDate = dto.referenceDate!!,
                    accountId = dto.accountId!!
            ) )


    fun toCustomAttributeDTO(dto: CustomAttributeRequestDTO): Mono<CustomAttributeRequest> =
            just( CustomAttributeRequest(
                    propertyName = dto.propertyName!!,
                    value = dto.value!!,
                    accountId = dto.accountId!!
            ) )
}