package com.coelhocaique.finance.core.service

import com.coelhocaique.finance.core.domain.dto.DebtDTO
import com.coelhocaique.finance.core.domain.mapper.DebtMapper.toDTO
import com.coelhocaique.finance.core.domain.mapper.DebtMapper.toMonoDTO
import com.coelhocaique.finance.core.persistance.DebtRepository
import com.coelhocaique.finance.core.service.helper.DebtHelper.generateDebts
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class DebtService(private val repository: DebtRepository) {

    fun create(dto: Mono<DebtDTO>): Mono<List<DebtDTO>> {
        return dto.map(::generateDebts)
                .flatMap { repository.insertAll(it)}
                .map { it.map { itt -> toDTO(itt) } }
    }

    fun findById(accountId: String, id: String): Mono<DebtDTO> {
        return repository.findById(id, accountId).flatMap(::toMonoDTO)
    }

    fun findByReferenceCode(accountId: String, referenceCode: String): Mono<List<DebtDTO>> {
        return repository.findByReferenceCode(referenceCode, accountId)
                        .map { it.map { itt -> toDTO(itt) }}
    }

    fun findByReferenceDate(accountId: String, referenceDate: String): Mono<List<DebtDTO>> {
        return repository.findByReferenceDate(referenceDate, accountId)
                .map { it.map { itt -> toDTO(itt) }}
    }

    fun findByReferenceDateRange(accountId: String, dateFrom: String, dateTo: String): Mono<List<DebtDTO>> {
        return repository.findByReferenceDateBetween(dateFrom, dateTo, accountId)
                .map { it.map { itt -> toDTO(itt) }}
    }

    fun deleteByReferenceCode(accountId: String, referenceCode: String): Mono<List<DebtDTO>> {
        return findByReferenceCode(accountId, referenceCode)
                .map {
                        it.forEach { r -> repository.deleteById(r.debtId.toString()) }
                        it
                }
    }

    fun deleteById(accountId: String, id: String): Mono<DebtDTO> {
        return findById(accountId, id)
                .map {  repository.deleteById(id)
                    it
                }
    }
}