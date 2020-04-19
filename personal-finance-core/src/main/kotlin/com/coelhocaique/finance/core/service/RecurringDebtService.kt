package com.coelhocaique.finance.core.service

import com.coelhocaique.finance.core.domain.dto.RecurringDebtDTO
import com.coelhocaique.finance.core.domain.mapper.RecurringDebtMapper.toDTO
import com.coelhocaique.finance.core.domain.mapper.RecurringDebtMapper.toDocument
import com.coelhocaique.finance.core.domain.mapper.RecurringDebtMapper.toMonoDTO
import com.coelhocaique.finance.core.persistance.RecurringDebtRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class RecurringDebtService(private val repository: RecurringDebtRepository) {

    fun create(dto: Mono<RecurringDebtDTO>): Mono<RecurringDebtDTO> {
        return dto.map { toDocument(it) }
                .flatMap { repository.insert(it) }
                .flatMap { toMonoDTO(it) }
    }

    fun findById(accountId: String, id: String): Mono<RecurringDebtDTO> {
        return repository.findById(id, accountId).flatMap(::toMonoDTO)
    }

    fun findAll(accountId: String): Mono<List<RecurringDebtDTO>> {
        return repository.findAll(accountId).map { it.map { itt -> toDTO(itt) }}
    }

    fun deleteById(accountId: String, id: String): Mono<RecurringDebtDTO> {
        return findById(accountId, id)
                .map {  repository.deleteById(id)
                    it
                }
    }
}