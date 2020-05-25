package com.coelhocaique.finance.core.service


import com.coelhocaique.finance.core.domain.dto.CustomAttributeRequest
import com.coelhocaique.finance.core.domain.dto.CustomAttributeResponse
import com.coelhocaique.finance.core.domain.mapper.CustomAttributeMapper.toDTO
import com.coelhocaique.finance.core.domain.mapper.CustomAttributeMapper.toDocument
import com.coelhocaique.finance.core.domain.mapper.CustomAttributeMapper.toMonoDTO
import com.coelhocaique.finance.core.persistance.CustomAttributeRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.*

@Service
class CustomAttributeService(private val repository: CustomAttributeRepository) {

    fun create(request: Mono<CustomAttributeRequest>): Mono<CustomAttributeResponse> {
        return request.flatMap { repository.insert(toDocument(it)) }
                .flatMap { toMonoDTO(it) }
    }

    fun findByPropertyName(accountId: UUID, propertyName: String): Mono<List<CustomAttributeResponse>> {
        return repository.findByPropertyName(propertyName,  accountId)
                .map { it.map { itt -> toDTO(itt) }}
    }

    fun findAll(accountId: UUID): Mono<List<CustomAttributeResponse>> {
        return repository.findAll(accountId)
                .map { it.map { itt -> toDTO(itt) }}
    }

    fun deleteById(accountId: UUID, id: UUID): Mono<CustomAttributeResponse> {
        return repository.findById(id, accountId)
                .flatMap {
                    repository.deleteById(id)
                    toMonoDTO(it)
                }
    }
}