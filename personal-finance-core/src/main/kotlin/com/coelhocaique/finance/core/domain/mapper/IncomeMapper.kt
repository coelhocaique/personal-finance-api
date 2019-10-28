package com.coelhocaique.finance.core.domain.mapper

import com.coelhocaique.finance.core.domain.Addition
import com.coelhocaique.finance.core.domain.Discount
import com.coelhocaique.finance.core.domain.Income
import com.coelhocaique.finance.core.domain.dto.AdditionDTO
import com.coelhocaique.finance.core.domain.dto.DiscountDTO
import com.coelhocaique.finance.core.domain.dto.IncomeDTO
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import java.util.*

object IncomeMapper {

    fun toDocument(incomeDTO: IncomeDTO): Income =
        Income(grossAmount = incomeDTO.grossAmount,
                netAmount = incomeDTO.netAmount!!,
                additionalAmount = incomeDTO.additionalAmount,
                description = incomeDTO.description,
                username = incomeDTO.username,
                receiptDate = incomeDTO.receiptDate,
                referenceDate = incomeDTO.referenceDate,
                sourceName = incomeDTO.sourceName.toUpperCase(),
                discountAmount = incomeDTO.discountAmount,
                discounts = incomeDTO.discounts.map(::toDocument),
                additions = incomeDTO.additions.map(::toDocument))

    fun toDTO(income: Income): IncomeDTO =
                IncomeDTO(
                        incomeId = UUID.fromString(income.id),
                        grossAmount = income.grossAmount,
                        netAmount = income.netAmount,
                        additionalAmount = income.additionalAmount,
                        description = income.description,
                        username = income.username,
                        receiptDate = income.receiptDate,
                        referenceDate = income.referenceDate,
                        sourceName = income.sourceName,
                        discountAmount = income.discountAmount,
                        discounts = income.discounts.map(::toDTO),
                        additions = income.additions.map(::toDTO),
                        creationDate = income.creationDate)

    fun toMonoDTO(income: Income): Mono<IncomeDTO> = just(toDTO(income))

    private fun toDTO(addition: Addition): AdditionDTO = AdditionDTO(addition.amount, addition.description)

    private fun toDTO(discount: Discount): DiscountDTO = DiscountDTO(discount.amount, discount.description)

    private fun toDocument(addition: AdditionDTO): Addition = Addition(addition.amount, addition.description)

    private fun toDocument(discount: DiscountDTO): Discount = Discount(discount.amount, discount.description)
}

