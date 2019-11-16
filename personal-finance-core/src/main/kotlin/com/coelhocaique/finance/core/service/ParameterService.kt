package com.coelhocaique.finance.core.service

import com.coelhocaique.finance.core.domain.dto.ParameterDTO
import com.coelhocaique.finance.core.domain.mapper.ParameterMapper.toDTO
import com.coelhocaique.finance.core.domain.mapper.ParameterMapper.toDocument
import com.coelhocaique.finance.core.domain.mapper.ParameterMapper.toMonoDTO
import com.coelhocaique.finance.core.persistance.ParameterRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ParameterService(private val repository: ParameterRepository) {

    fun create(dto: Mono<ParameterDTO>): Mono<ParameterDTO> {
        return dto.flatMap { repository.insert(toDocument(it)) }
                .flatMap { toMonoDTO(it) }
    }

    fun findById(accountId: String, id: String): Mono<ParameterDTO> {
        return repository.findById(id, accountId).flatMap { toMonoDTO(it) }
    }

    fun findByReferenceDate(accountId: String, referenceDate: String): Mono<List<ParameterDTO>> {
        return repository.findByReferenceDate(referenceDate,  accountId)
                .map { it.map { itt -> toDTO(itt) }}
    }

    fun deleteById(accountId: String, id: String): Mono<ParameterDTO> {
        return findById(accountId, id)
                .map {
                    repository.deleteById(id)
                    it
                }
    }

    fun findByReferenceDateRange(accountId: String, dateFrom: String, dateTo: String): Mono<List<ParameterDTO>> {
        return repository.findByReferenceDateBetween(dateFrom, dateTo, accountId)
                .map { it.map { itt -> toDTO(itt) }}
    }
}