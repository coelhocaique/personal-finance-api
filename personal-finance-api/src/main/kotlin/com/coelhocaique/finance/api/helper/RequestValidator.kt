package com.coelhocaique.finance.api.helper

import com.coelhocaique.finance.api.dto.DebtRequestDTO
import com.coelhocaique.finance.api.dto.IncomeRequestDTO
import com.coelhocaique.finance.api.dto.ParameterRequestDTO
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just

object RequestValidator {
    fun validate(dto: DebtRequestDTO): Mono<DebtRequestDTO> {
        return just(dto)
    }

    fun validate(dto: IncomeRequestDTO): Mono<IncomeRequestDTO> {
        return just(dto)
    }

    fun validate(dto: ParameterRequestDTO): Mono<ParameterRequestDTO> {
        return just(dto)
    }

}