package com.coelhocaique.finance.core.service

import com.coelhocaique.finance.core.domain.dto.IncomeDTO
import com.coelhocaique.finance.core.domain.mapper.IncomeMapper.toDTO
import com.coelhocaique.finance.core.domain.mapper.IncomeMapper.toDocument
import com.coelhocaique.finance.core.domain.mapper.IncomeMapper.toMonoDTO
import com.coelhocaique.finance.core.persistance.IncomeRepository
import com.coelhocaique.finance.core.service.helper.IncomeHelper.calculateIncome
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class IncomeService(private val repository: IncomeRepository) {

    fun create(incomeDTO: Mono<IncomeDTO>): Mono<IncomeDTO> {
        return incomeDTO.map(::calculateIncome)
                .flatMap { repository.insert(toDocument(it)) }
                .flatMap { toMonoDTO(it) }
    }

    fun findById(accountId: String, id: String): Mono<IncomeDTO> {
        return repository.findById(id, accountId).flatMap { toMonoDTO(it) }
    }

    fun findByReferenceDate(accountId: String, referenceDate: String): Mono<List<IncomeDTO>> {
        return repository.findByReferenceDate(referenceDate, accountId)
                .map { it.map { itt -> toDTO(itt) }}
    }

    fun findByReferenceDateRange(accountId: String, dateFrom: String, dateTo: String): Mono<List<IncomeDTO>> {
        return repository.findByReferenceDateBetween(dateFrom, dateTo, accountId)
                .map { it.map { itt -> toDTO(itt) }}
    }

    fun deleteById(accountId: String, id: String): Mono<IncomeDTO> {
        return findById(accountId, id)
                .map {
                    repository.deleteById(id)
                    it
                }
    }
}