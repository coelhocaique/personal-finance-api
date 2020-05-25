package com.coelhocaique.finance.core.service

import com.coelhocaique.finance.core.domain.dto.RecurringDebtRequest
import com.coelhocaique.finance.core.domain.dto.RecurringDebtResponse
import com.coelhocaique.finance.core.domain.mapper.RecurringDebtMapper.toDTO
import com.coelhocaique.finance.core.domain.mapper.RecurringDebtMapper.toDocument
import com.coelhocaique.finance.core.domain.mapper.RecurringDebtMapper.toMonoDTO
import com.coelhocaique.finance.core.persistance.RecurringDebtRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.*

@Service
class RecurringDebtService(private val repository: RecurringDebtRepository) {

    fun create(dto: Mono<RecurringDebtRequest>): Mono<RecurringDebtResponse> {
        return dto.map { toDocument(it) }
                .flatMap { repository.insert(it) }
                .flatMap { toMonoDTO(it) }
    }

    fun findById(accountId: UUID, id: UUID): Mono<RecurringDebtResponse> {
        return repository.findById(id, accountId).flatMap(::toMonoDTO)
    }

    fun findAll(accountId: UUID): Mono<List<RecurringDebtResponse>> {
        return repository.findAll(accountId).map { it.map { itt -> toDTO(itt) } }
    }

    fun deleteById(accountId: UUID, id: UUID): Mono<RecurringDebtResponse> {
        return findById(accountId, id)
                .map {
                    repository.deleteById(id)
                    it
                }
    }
}