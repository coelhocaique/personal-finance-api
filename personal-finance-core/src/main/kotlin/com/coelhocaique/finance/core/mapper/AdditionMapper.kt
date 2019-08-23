package com.coelhocaique.finance.core.mapper

import com.coelhocaique.finance.core.document.Addition
import com.coelhocaique.finance.core.dto.AdditionDTO
import reactor.core.publisher.Mono
import java.time.LocalDateTime

object AdditionMapper {

    fun toDocument(additionDTO: AdditionDTO, incomeId: String) : Mono<Addition>{
        return Mono.just(Addition(
                incomeId = incomeId,
                amount = additionDTO.amount,
                description = additionDTO.description,
                creationDate = LocalDateTime.now()))
    }

    fun toDTO(addition: Addition) : Mono<AdditionDTO> {
        return Mono.just(AdditionDTO(
                id = addition.id,
                incomeId = addition.incomeId,
                amount = addition.amount,
                description = addition.description))
    }
}