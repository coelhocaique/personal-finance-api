package com.coelhocaique.finance.core.service

import com.coelhocaique.finance.core.domain.dto.DebtDTO
import com.coelhocaique.finance.core.domain.mapper.DebtMapper
import com.coelhocaique.finance.core.domain.mapper.DebtMapper.toDTO
import com.coelhocaique.finance.core.domain.mapper.DebtMapper.toMonoDTO
import com.coelhocaique.finance.core.persistance.DebtRepository
import com.coelhocaique.finance.core.service.helper.DebtHelper.generateDebts
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import java.util.*

@Service
class DebtService(private val repository: DebtRepository) {

    fun create(dto: Mono<DebtDTO>): Mono<List<DebtDTO>> {
        return dto.map(::generateDebts)
                .flatMap { repository.insert(it).collectList()}
                .map { it.map { itt -> toDTO(itt) } }
    }

    fun findById(debtId: String): Mono<DebtDTO> {
        return repository.findById(debtId).flatMap(::toMonoDTO)
    }

    fun findByReferenceCode(referenceCode: String): Mono<List<DebtDTO>> {
        return repository.findByReferenceCode(referenceCode)
                        .collectList()
                        .map { it.map { itt -> toDTO(itt) }}
    }

    fun findByReferenceDate(referenceDate: String): Mono<List<DebtDTO>> {
        return repository.findByReferenceDate(referenceDate)
                .collectList()
                .map { it.map { itt -> toDTO(itt) }}
    }

    fun findByReferenceDateBetween(referenceFrom: String, referenceTo: String): Mono<List<DebtDTO>> {
        return repository.findByReferenceDateRange(referenceFrom, referenceTo)
                .collectList()
                .map { it.map { itt -> toDTO(itt) }}
    }
}