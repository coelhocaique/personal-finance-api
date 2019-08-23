package com.coelhocaique.finance.core.service

import com.coelhocaique.finance.core.dto.IncomeDTO
import com.coelhocaique.finance.core.mapper.IncomeMapper
import com.coelhocaique.finance.core.repository.IncomeRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.concurrent.CompletableFuture

@Service
class IncomeService(private val incomeRepository: IncomeRepository,
                    private val discountService: DiscountService,
                    private val additionService: AdditionService) {

    fun create(incomeDTO: Mono<IncomeDTO>): Mono<IncomeDTO> {
        val additions = incomeDTO.block()!!.additions
        val discounts = incomeDTO.block()!!.discounts

        return incomeDTO.flatMap { IncomeMapper.toDocument(it) }
                .flatMap { incomeRepository.save(it) }
                .flatMap { IncomeMapper.toDTO(it) }
                .doOnSuccess {
                    CompletableFuture.runAsync {
                        discountService.create(discounts, it.id!!)
                        additionService.create(additions, it.id!!)
                    }
                 }
    }

    fun findById(id: String): Mono<IncomeDTO> {
        return incomeRepository.findById(id)
                .flatMap { IncomeMapper.toDTO(it) }
    }
}