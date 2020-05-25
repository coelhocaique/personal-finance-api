package com.coelhocaique.finance.core.service

import com.coelhocaique.finance.core.domain.dto.DebtRequest
import com.coelhocaique.finance.core.domain.dto.DebtResponse
import com.coelhocaique.finance.core.domain.mapper.DebtMapper.toDTO
import com.coelhocaique.finance.core.domain.mapper.DebtMapper.toMonoDTO
import com.coelhocaique.finance.core.persistance.DebtRepository
import com.coelhocaique.finance.core.service.helper.DebtHelper.generateDebts
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.*

@Service
class DebtService(private val repository: DebtRepository) {

    fun create(dto: Mono<DebtRequest>): Mono<List<DebtResponse>> {
        return dto.map(::generateDebts)
                .flatMap { repository.insertAll(it) }
                .map { it.map { itt -> toDTO(itt) } }
    }

    fun findById(accountId: UUID, id: UUID): Mono<DebtResponse> {
        return repository.findById(id, accountId).flatMap(::toMonoDTO)
    }

    fun findByReferenceCode(accountId: UUID, referenceCode: UUID): Mono<List<DebtResponse>> {
        return repository.findByReferenceCode(referenceCode, accountId)
                .map { it.map { itt -> toDTO(itt) } }
    }

    fun findByReferenceDate(accountId: UUID, referenceDate: String): Mono<List<DebtResponse>> {
        return repository.findByReferenceDate(referenceDate, accountId)
                .map { it.map { itt -> toDTO(itt) } }
    }

    fun findByReferenceDateRange(accountId: UUID, dateFrom: String, dateTo: String): Mono<List<DebtResponse>> {
        return repository.findByReferenceDateBetween(dateFrom, dateTo, accountId)
                .map { it.map { itt -> toDTO(itt) } }
    }

    fun deleteByReferenceCode(accountId: UUID, referenceCode: UUID): Mono<List<DebtResponse>> {
        return findByReferenceCode(accountId, referenceCode)
                .map {
                    it.forEach { r -> repository.deleteById(r.debtId) }
                    it
                }
    }

    fun deleteById(accountId: UUID, id: UUID): Mono<DebtResponse> {
        return findById(accountId, id)
                .map {
                    repository.deleteById(id)
                    it
                }
    }
}