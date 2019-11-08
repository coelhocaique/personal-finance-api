package com.coelhocaique.finance.core.service


import com.coelhocaique.finance.core.domain.dto.CustomAttributeDTO
import com.coelhocaique.finance.core.domain.mapper.CustomAttributeMapper.toDTO
import com.coelhocaique.finance.core.domain.mapper.CustomAttributeMapper.toDocument
import com.coelhocaique.finance.core.domain.mapper.CustomAttributeMapper.toMonoDTO
import com.coelhocaique.finance.core.persistance.CustomAttributeRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.empty
import reactor.core.publisher.Mono.just

@Service
class CustomAttributeService(private val repository: CustomAttributeRepository) {

    fun create(dto: Mono<CustomAttributeDTO>): Mono<CustomAttributeDTO> {
        return dto.flatMap { just(repository.save(toDocument(it))) }
                .flatMap { toMonoDTO(it) }
    }

    fun findByPropertyName(accountId: String, propertyName: String): Mono<List<CustomAttributeDTO>> {
        return just(repository.findByPropertyNameAndAccountId(propertyName,  accountId))
                .map { it.map { itt -> toDTO(itt) }}
    }

    fun findAll(accountId: String): Mono<List<CustomAttributeDTO>> {
        return just(repository.findByAccountId(accountId))
                .map { it.map { itt -> toDTO(itt) }}
    }

    fun deleteById(accountId: String, id: String): Mono<CustomAttributeDTO> {
        return repository.findByIdAndAccountId(accountId, id)
                .map {
                    repository.deleteByIdAndAccountId(it.accountId.toString(), it.accountId!!)
                    toMonoDTO(it)
                }.orElse(empty())
    }
}