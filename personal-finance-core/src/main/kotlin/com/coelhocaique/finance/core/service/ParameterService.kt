package com.coelhocaique.finance.core.service

import com.coelhocaique.finance.core.domain.dto.ParameterRequest
import com.coelhocaique.finance.core.domain.dto.ParameterResponse
import com.coelhocaique.finance.core.domain.mapper.ParameterMapper.toDocument
import com.coelhocaique.finance.core.domain.mapper.ParameterMapper.toMonoResponse
import com.coelhocaique.finance.core.domain.mapper.ParameterMapper.toResponse
import com.coelhocaique.finance.core.persistance.ParameterRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.*

@Service
class ParameterService(private val repository: ParameterRepository) {

    fun create(dto: Mono<ParameterRequest>): Mono<ParameterResponse> {
        return dto.flatMap { repository.insert(toDocument(it)) }
                .flatMap { toMonoResponse(it) }
    }

    fun findById(accountId: UUID, id: UUID): Mono<ParameterResponse> {
        return repository.findById(id, accountId).flatMap { toMonoResponse(it) }
    }

    fun findByReferenceDate(accountId: UUID, referenceDate: String): Mono<List<ParameterResponse>> {
        return repository.findByReferenceDate(referenceDate, accountId)
                .map { it.map { itt -> toResponse(itt) } }
    }

    fun findByNameAndReferenceDate(accountId: UUID, name: String, referenceDate: String): Mono<List<ParameterResponse>> {
        return repository.findByNameAndReferenceDate(name, referenceDate, accountId)
                .map { it.map { itt -> toResponse(itt) } }
    }

    fun findByNameAndReferenceDateRange(accountId: UUID, name: String, dateFrom: String, dateTo: String):
            Mono<List<ParameterResponse>> {
        return repository.findByNameAndReferenceDateBetween(name, dateFrom, dateTo, accountId)
                .map { it.map { itt -> toResponse(itt) } }
    }

    fun findByName(accountId: UUID, name: String): Mono<List<ParameterResponse>> {
        return repository.findByName(name, accountId)
                .map { it.map { itt -> toResponse(itt) } }
    }

    fun deleteById(accountId: UUID, id: UUID): Mono<ParameterResponse> {
        return findById(accountId, id)
                .map {
                    repository.deleteById(id)
                    it
                }
    }

    fun findByReferenceDateRange(accountId: UUID, dateFrom: String, dateTo: String): Mono<List<ParameterResponse>> {
        return repository.findByReferenceDateBetween(dateFrom, dateTo, accountId)
                .map { it.map { itt -> toResponse(itt) } }
    }
}