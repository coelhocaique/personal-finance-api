package com.coelhocaique.finance.api.mapper

import com.coelhocaique.finance.api.dto.IncomeRequestDTO
import com.coelhocaique.finance.core.dto.IncomeDTO
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just

object IncomeMapper {

    fun toIncomeDTO(incomeRequestDTO: IncomeRequestDTO) =
            just( IncomeDTO(
                    grossAmount = incomeRequestDTO.grossAmount!!,
                    description = incomeRequestDTO.description!!,
                    username = incomeRequestDTO.username,
                    referenceDate = incomeRequestDTO.referenceDate!!,
                    receiptDate = incomeRequestDTO.receiptDate!!,
                    sourceName = incomeRequestDTO.sourceName!!,
                    discounts = incomeRequestDTO.discounts,
                    additions = incomeRequestDTO.additions
            ) )
}