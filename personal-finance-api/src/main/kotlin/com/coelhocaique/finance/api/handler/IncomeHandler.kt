package com.coelhocaique.finance.api.handler

import com.coelhocaique.finance.api.dto.IncomeRequestDTO
import com.coelhocaique.finance.api.mapper.IncomeMapper
import com.coelhocaique.finance.core.service.IncomeService
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class IncomeHandler (private val incomeService: IncomeService) {

    fun create(incomeRequestDTO: Mono<IncomeRequestDTO>) =
            incomeRequestDTO.flatMap { incomeService.create(IncomeMapper.toIncomeDTO(it)) }
                            .flatMap { IncomeMapper.toIncomeResponseDTO(it) }

    fun findById(incomeId: Mono<String>) =
            incomeId.flatMap { incomeService.findById(it) }
                    .flatMap { IncomeMapper.toIncomeResponseDTO(it) }
}