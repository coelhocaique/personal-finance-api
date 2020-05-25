package com.coelhocaique.finance.core.service

import com.coelhocaique.finance.core.domain.dto.IncomeRequest
import com.coelhocaique.finance.core.domain.dto.IncomeResponse
import com.coelhocaique.finance.core.domain.mapper.IncomeMapper.toDTO
import com.coelhocaique.finance.core.domain.mapper.IncomeMapper.toMonoDTO
import com.coelhocaique.finance.core.persistance.IncomeRepository
import com.coelhocaique.finance.core.service.helper.IncomeHelper.generateIncome
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.*

@Service
class IncomeService(private val repository: IncomeRepository) {

    fun create(request: Mono<IncomeRequest>): Mono<IncomeResponse> {
        return request.map(::generateIncome)
                .flatMap { repository.insert(it) }
                .flatMap { toMonoDTO(it) }
    }

    fun findById(accountId: UUID, id: UUID): Mono<IncomeResponse> {
        return repository.findById(id, accountId).flatMap { toMonoDTO(it) }
    }

    fun findByReferenceDate(accountId: UUID, referenceDate: String): Mono<List<IncomeResponse>> {
        return repository.findByReferenceDate(referenceDate, accountId)
                .map { it.map { itt -> toDTO(itt) } }
    }

    fun findByReferenceDateRange(accountId: UUID, dateFrom: String, dateTo: String): Mono<List<IncomeResponse>> {
        return repository.findByReferenceDateBetween(dateFrom, dateTo, accountId)
                .map { it.map { itt -> toDTO(itt) } }
    }

    fun deleteById(accountId: UUID, id: UUID): Mono<IncomeResponse> {
        return findById(accountId, id)
                .map {
                    repository.deleteById(id)
                    it
                }
    }
}