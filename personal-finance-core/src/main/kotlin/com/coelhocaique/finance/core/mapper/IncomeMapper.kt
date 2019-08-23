package com.coelhocaique.finance.core.mapper

import com.coelhocaique.finance.core.document.Income
import com.coelhocaique.finance.core.dto.IncomeDTO
import reactor.core.publisher.Mono
import java.time.LocalDateTime

object IncomeMapper {

    fun toDocument(incomeDTO: IncomeDTO): Mono<Income> {
        return Mono.just(
                Income( grossAmount = incomeDTO.grossAmount,
                        netAmount = incomeDTO.netAmount,
                        extraAmount = incomeDTO.extraAmount,
                        description = incomeDTO.description,
                        owner = incomeDTO.owner,
                        date = incomeDTO.date,
                        referenceMonth = incomeDTO.referenceMonth,
                        referenceYear = incomeDTO.referenceYear,
                        discountAmount = incomeDTO.discountAmount,
                        companyName = incomeDTO.companyName,
                        creationDate = LocalDateTime.now()
                )
        )
    }

    fun toDTO(income: Income):Mono<IncomeDTO> {
        return Mono.just(
                IncomeDTO(
                        id = income.id,
                        grossAmount = income.grossAmount,
                        netAmount = income.netAmount,
                        extraAmount = income.extraAmount,
                        description = income.description,
                        owner = income.owner,
                        date = income.date,
                        referenceMonth = income.referenceMonth,
                        referenceYear = income.referenceYear,
                        discountAmount = income.discountAmount,
                        companyName = income.companyName,
                        creationDate = income.creationDate)
        )
    }
}

