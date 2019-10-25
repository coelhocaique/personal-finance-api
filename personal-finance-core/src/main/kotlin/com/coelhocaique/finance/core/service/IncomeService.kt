package com.coelhocaique.finance.core.service

import com.coelhocaique.finance.core.dto.IncomeDTO
import com.coelhocaique.finance.core.mapper.IncomeMapper.toDTO
import com.coelhocaique.finance.core.mapper.IncomeMapper.toDocument
import com.coelhocaique.finance.core.repository.IncomeRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.math.BigDecimal

@Service
class IncomeService(private val repository: IncomeRepository) {

    fun create(incomeDTO: Mono<IncomeDTO>): Mono<IncomeDTO> {
        return incomeDTO.map(::calculateIncome)
                .flatMap { repository.insert(toDocument(it)) }
                .flatMap { toDTO(it) }
    }

    fun findById(id: String): Mono<IncomeDTO> {
        return repository.findById(id).flatMap { toDTO(it) }
    }

    private fun calculateIncome(incomeDTO: IncomeDTO): IncomeDTO {
        val discountAmount = incomeDTO.discounts.fold(BigDecimal.ZERO) { x, it -> x.add(it.amount) }
        val additionalAmount = incomeDTO.additions.fold(BigDecimal.ZERO) { x, it -> x.add(it.amount) }
        val netAmount = incomeDTO.grossAmount.add(additionalAmount).subtract(discountAmount)
        return incomeDTO.copy(netAmount = netAmount, additionalAmount = additionalAmount,
                discountAmount = discountAmount)
    }
}