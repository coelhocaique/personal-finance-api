package com.coelhocaique.finance.api.helper

import com.coelhocaique.finance.api.dto.DebtRequestDTO
import com.coelhocaique.finance.api.dto.IncomeRequestDTO
import reactor.core.publisher.Mono

object RequestValidator {
    fun validate(dto: DebtRequestDTO): Mono<DebtRequestDTO> {
        return Mono.empty()
    }

    fun validate(dto: IncomeRequestDTO): Mono<IncomeRequestDTO> {
        return Mono.empty()
    }

}