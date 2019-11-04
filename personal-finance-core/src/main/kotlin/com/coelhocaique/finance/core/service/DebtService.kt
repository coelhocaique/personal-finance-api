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
import reactor.core.publisher.Mono.empty
import reactor.core.publisher.Mono.just
import reactor.core.publisher.toMono
import java.util.*

@Service
class DebtService(private val repository: DebtRepository) {

    fun create(dto: Mono<DebtDTO>): Mono<List<DebtDTO>> {
        return dto.map(::generateDebts)
                .flatMap { just(repository.saveAll(it))}
                .map { it.map { itt -> toDTO(itt) } }
    }

    fun findById(userId: String, id: String): Mono<DebtDTO> {
        return repository.findByIdAndUserId(id, userId).map(::toMonoDTO).orElse(empty())
    }

    fun findByReferenceCode(userId: String, referenceCode: String): Mono<List<DebtDTO>> {
        return just(repository.findByReferenceCodeAndUserId(referenceCode, userId))
                        .map { it.map { itt -> toDTO(itt) }}
    }

    fun findByReferenceDate(userId: String, referenceDate: String): Mono<List<DebtDTO>> {
        return just(repository.findByReferenceDateAndUserId(referenceDate, userId))
                .map { it.map { itt -> toDTO(itt) }}
    }

    fun findByReferenceDateBetween(userId: String, referenceFrom: String, referenceTo: String): Mono<List<DebtDTO>> {
        return just(repository.findByReferenceDateBetweenAndUserId(referenceFrom, referenceTo, userId))
                .map { it.map { itt -> toDTO(itt) }}
    }
}