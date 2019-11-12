package com.coelhocaique.finance.core.service

import com.coelhocaique.finance.core.domain.dto.ParameterDTO
import com.coelhocaique.finance.core.domain.mapper.ParameterMapper.toDTO
import com.coelhocaique.finance.core.domain.mapper.ParameterMapper.toDocument
import com.coelhocaique.finance.core.domain.mapper.ParameterMapper.toMonoDTO
import com.coelhocaique.finance.core.persistance.ParameterRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.empty
import reactor.core.publisher.Mono.just

@Service
class ParameterService(private val repository: ParameterRepository) {

    fun create(dto: Mono<ParameterDTO>): Mono<ParameterDTO> {
        return dto.flatMap { just(repository.save(toDocument(it))) }
                .flatMap { toMonoDTO(it) }
    }

    fun findById(accountId: String, id: String): Mono<ParameterDTO> {
        return repository.findByIdAndAccountId(id, accountId).map { toMonoDTO(it) }.orElse(empty())
    }

    fun findByReferenceDate(accountId: String, referenceDate: String): Mono<List<ParameterDTO>> {
        return just(repository.findByReferenceDateAndAccountId(referenceDate,  accountId))
                .map { it.map { itt -> toDTO(itt) }}
    }

    fun deleteById(accountId: String, id: String): Mono<ParameterDTO> {
        return findById(accountId, id)
                .map {
                    repository.deleteByIdAndAccountId(id, accountId)
                    it
                }
    }

    fun findByReferenceDateRange(accountId: String, dateFrom: String, dateTo: String): Mono<List<ParameterDTO>> {
        return just(repository.findByReferenceDateBetweenAndAccountId(dateFrom, dateTo, accountId))
                .map { it.map { itt -> toDTO(itt) }}
    }
}