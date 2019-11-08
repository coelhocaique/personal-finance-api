package com.coelhocaique.finance.core.service

import com.coelhocaique.finance.core.domain.dto.DebtDTO
import com.coelhocaique.finance.core.domain.mapper.DebtMapper.toDTO
import com.coelhocaique.finance.core.domain.mapper.DebtMapper.toMonoDTO
import com.coelhocaique.finance.core.persistance.DebtRepository
import com.coelhocaique.finance.core.service.helper.DebtHelper.generateDebts
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.empty
import reactor.core.publisher.Mono.just

@Service
class DebtService(private val repository: DebtRepository) {

    fun create(dto: Mono<DebtDTO>): Mono<List<DebtDTO>> {
        return dto.map(::generateDebts)
                .flatMap { just(repository.saveAll(it))}
                .map { it.map { itt -> toDTO(itt) } }
    }

    fun findById(accountId: String, id: String): Mono<DebtDTO> {
        return repository.findByIdAndAccountId(id, accountId).map(::toMonoDTO).orElse(empty())
    }

    fun findByReferenceCode(accountId: String, referenceCode: String): Mono<List<DebtDTO>> {
        return just(repository.findByReferenceCodeAndAccountId(referenceCode, accountId))
                        .map { it.map { itt -> toDTO(itt) }}
    }

    fun findByReferenceDate(accountId: String, referenceDate: String): Mono<List<DebtDTO>> {
        return just(repository.findByReferenceDateAndAccountId(referenceDate, accountId))
                .map { it.map { itt -> toDTO(itt) }}
    }

    fun findByReferenceDateRange(accountId: String, dateFrom: String, dateTo: String): Mono<List<DebtDTO>> {
        return just(repository.findByReferenceDateBetweenAndAccountId(dateFrom, dateTo, accountId))
                .map { it.map { itt -> toDTO(itt) }}
    }

    fun deleteByReferenceCode(accountId: String, referenceCode: String): Mono<List<DebtDTO>> {
        return findByReferenceCode(accountId, referenceCode)
                .map {  repository.deleteByReferenceCodeAndAccountId(referenceCode, accountId)
                        it
                }
    }

    fun deleteById(accountId: String, id: String): Mono<DebtDTO> {
        return findById(accountId, id)
                .map {  repository.deleteByIdAndAccountId(it.debtId.toString(), it.accountId!!)
                    it
                }
    }
}