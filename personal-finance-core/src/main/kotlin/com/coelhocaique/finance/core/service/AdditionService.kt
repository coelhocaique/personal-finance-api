package com.coelhocaique.finance.core.service

import com.coelhocaique.finance.core.dto.AdditionDTO
import com.coelhocaique.finance.core.mapper.AdditionMapper
import com.coelhocaique.finance.core.repository.AdditionRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class AdditionService(private val additionRepository: AdditionRepository) {

    fun create(additionDTOs: List<AdditionDTO>, incomeId: String): Flux<AdditionDTO> {
        return Flux.create<AdditionDTO> { additionDTOs }
                .flatMap { AdditionMapper.toDocument(it, incomeId) }
                .flatMap { additionRepository.save(it) }
                .flatMap { AdditionMapper.toDTO(it) }
    }
}