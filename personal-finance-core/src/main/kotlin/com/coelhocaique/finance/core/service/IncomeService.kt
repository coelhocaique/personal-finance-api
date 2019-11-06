package com.coelhocaique.finance.core.service

import com.coelhocaique.finance.core.domain.dto.IncomeDTO
import com.coelhocaique.finance.core.domain.mapper.IncomeMapper.toDTO
import com.coelhocaique.finance.core.domain.mapper.IncomeMapper.toDocument
import com.coelhocaique.finance.core.domain.mapper.IncomeMapper.toMonoDTO
import com.coelhocaique.finance.core.persistance.IncomeRepository
import com.coelhocaique.finance.core.service.helper.IncomeHelper.calculateIncome
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.empty
import reactor.core.publisher.Mono.just

@Service
class IncomeService(private val repository: IncomeRepository) {

    fun create(incomeDTO: Mono<IncomeDTO>): Mono<IncomeDTO> {
        return incomeDTO.map(::calculateIncome)
                .flatMap { just(repository.save(toDocument(it))) }
                .flatMap { toMonoDTO(it) }
    }

    fun findById(userId: String, id: String): Mono<IncomeDTO> {
        return repository.findByIdAndUserId(id, userId).map { toMonoDTO(it) }.orElse(empty())
    }

    fun findByReferenceDate(userId: String, referenceDate: String): Mono<List<IncomeDTO>> {
        return just(repository.findByReferenceDateAndUserId(referenceDate, userId))
                .map { it.map { itt -> toDTO(itt) }}
    }

    fun findByReferenceDateRange(userId: String, dateFrom: String, dateTo: String): Mono<List<IncomeDTO>> {
        return just(repository.findByReferenceDateBetweenAndUserId(dateFrom, dateTo, userId))
                .map { it.map { itt -> toDTO(itt) }}
    }

    fun deleteById(userId: String, id: String): Mono<IncomeDTO> {
        return findById(userId, id)
                .map {
                    repository.deleteByIdAndUserId(it.incomeId.toString(), it.userId)
                    it
                }
    }
}