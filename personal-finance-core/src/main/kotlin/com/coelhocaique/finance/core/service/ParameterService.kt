package com.coelhocaique.finance.core.service

import com.coelhocaique.finance.core.domain.dto.ParameterDTO
import com.coelhocaique.finance.core.domain.mapper.IncomeMapper
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

    fun findById(userId: String, id: String): Mono<ParameterDTO> {
        return repository.findByIdAndUserId(id, userId).map { toMonoDTO(it) }.orElse(empty())
    }

    fun findByReferenceDate(userId: String, referenceDate: String): Mono<List<ParameterDTO>> {
        return just(repository.findByReferenceDateAndUserId(referenceDate,  userId))
                .map { it.map { itt -> toDTO(itt) }}
    }

    fun deleteById(userId: String, id: String): Mono<ParameterDTO> {
        return findById(userId, id)
                .map {
                    repository.deleteByIdAndUserId(it.parameterId.toString(), it.userId)
                    it
                }
    }

    fun findByReferenceDateRange(userId: String, dateFrom: String, dateTo: String): Mono<List<ParameterDTO>> {
        return just(repository.findByReferenceDateBetweenAndUserId(dateFrom, dateTo, userId))
                .map { it.map { itt -> toDTO(itt) }}
    }
}