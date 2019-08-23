package com.coelhocaique.finance.core.service

import com.coelhocaique.finance.core.dto.DiscountDTO
import com.coelhocaique.finance.core.mapper.DiscountMapper
import com.coelhocaique.finance.core.repository.DiscountRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class DiscountService(private val discountRepository: DiscountRepository) {

    fun create(discountDTOs: List<DiscountDTO>, incomeId: String): Flux<DiscountDTO>{
        return Flux.create<DiscountDTO> { discountDTOs }
                .flatMap { DiscountMapper.toDocument(it, incomeId) }
                .flatMap { discountRepository.save(it) }
                .flatMap { DiscountMapper.toDTO(it) }
    }
}