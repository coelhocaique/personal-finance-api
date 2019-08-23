package com.coelhocaique.finance.core.mapper

import com.coelhocaique.finance.core.document.Discount
import com.coelhocaique.finance.core.dto.DiscountDTO
import reactor.core.publisher.Mono
import java.time.LocalDateTime

object DiscountMapper {

    fun toDocument(additionDTO: DiscountDTO, incomeId: String) : Mono<Discount>{
        return Mono.just(Discount(
                incomeId = incomeId,
                amount = additionDTO.amount,
                description = additionDTO.description,
                creationDate = LocalDateTime.now()))
    }

    fun toDTO(addition: Discount) : Mono<DiscountDTO> {
        return Mono.just(DiscountDTO(
                id = addition.id,
                incomeId = addition.incomeId,
                amount = addition.amount,
                description = addition.description))
    }
}