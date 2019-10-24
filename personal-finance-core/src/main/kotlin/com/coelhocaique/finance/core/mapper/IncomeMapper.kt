package com.coelhocaique.finance.core.mapper

import com.coelhocaique.finance.core.document.Addition
import com.coelhocaique.finance.core.document.Discount
import com.coelhocaique.finance.core.document.Income
import com.coelhocaique.finance.core.dto.AdditionDTO
import com.coelhocaique.finance.core.dto.DiscountDTO
import com.coelhocaique.finance.core.dto.IncomeDTO
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just

object IncomeMapper {

    fun toDocument(incomeDTO: IncomeDTO): Income =
        Income(grossAmount = incomeDTO.grossAmount,
                netAmount = incomeDTO.netAmount!!,
                additionalAmount = incomeDTO.additionalAmount,
                description = incomeDTO.description,
                username = incomeDTO.username,
                receiptDate = incomeDTO.receiptDate,
                referenceDate = incomeDTO.referenceDate,
                sourceName = incomeDTO.sourceName,
                discountAmount = incomeDTO.discountAmount,
                discounts = incomeDTO.discounts.map(::toDocument),
                additions = incomeDTO.additions.map(::toDocument))

    fun toDTO(income: Income): Mono<IncomeDTO> =
        just (  IncomeDTO(
                        incomeId = income.id,
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
                        creationDate = income.creationDate) )


    private fun toDTO(addition: Addition): AdditionDTO = AdditionDTO(addition.amount, addition.description)

    private fun toDTO(discount: Discount): DiscountDTO = DiscountDTO(discount.amount, discount.description)

    private fun toDocument(addition: AdditionDTO): Addition = Addition(addition.amount, addition.description)

    private fun toDocument(discount: DiscountDTO): Discount = Discount(discount.amount, discount.description)
}

