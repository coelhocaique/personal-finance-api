package com.coelhocaique.finance.core.service


import com.coelhocaique.finance.core.domain.dto.CustomAttributeDTO
import com.coelhocaique.finance.core.domain.mapper.CustomAttributeMapper.toDTO
import com.coelhocaique.finance.core.domain.mapper.CustomAttributeMapper.toDocument
import com.coelhocaique.finance.core.domain.mapper.CustomAttributeMapper.toMonoDTO
import com.coelhocaique.finance.core.persistance.CustomAttributeRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class CustomAttributeService(private val repository: CustomAttributeRepository) {

    fun create(dto: Mono<CustomAttributeDTO>): Mono<CustomAttributeDTO> {
        return dto.flatMap { repository.insert(toDocument(it)) }
                .flatMap { toMonoDTO(it) }
    }

    fun findByPropertyName(accountId: String, propertyName: String): Mono<List<CustomAttributeDTO>> {
        return repository.findByPropertyName(propertyName,  accountId)
                .map { it.map { itt -> toDTO(itt) }}
    }

    fun findAll(accountId: String): Mono<List<CustomAttributeDTO>> {
        return repository.findAll(accountId)
                .map { it.map { itt -> toDTO(itt) }}
    }

    fun deleteById(accountId: String, id: String): Mono<CustomAttributeDTO> {
        return repository.findById(id, accountId)
                .flatMap {
                    repository.deleteById(id)
                    toMonoDTO(it)
                }
    }
}