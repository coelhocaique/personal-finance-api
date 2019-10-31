package com.coelhocaique.finance.core.service

import com.coelhocaique.finance.core.domain.dto.ParameterDTO
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

    fun findById(id: String): Mono<ParameterDTO> {
        return repository.findById(id).map { toMonoDTO(it) }.orElse(empty())
    }

}