package com.coelhocaique.finance.core.domain.mapper

import com.coelhocaique.finance.core.domain.Addition
import com.coelhocaique.finance.core.domain.Discount
import com.coelhocaique.finance.core.domain.Income
import com.coelhocaique.finance.core.domain.dto.AdditionDTO
import com.coelhocaique.finance.core.domain.dto.DiscountDTO
import com.coelhocaique.finance.core.domain.dto.IncomeRequest
import com.coelhocaique.finance.core.domain.dto.IncomeResponse
import com.coelhocaique.finance.core.util.generateReferenceDate
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

object IncomeMapper {

    fun toDocument(
            incomeDTO: IncomeRequest,
            netAmount: BigDecimal,
            additionalAmount: BigDecimal,
            discountAmount: BigDecimal,
            referenceDate: String
    ): Income = Income(
            incomeId = UUID.randomUUID(),
            grossAmount = incomeDTO.grossAmount,
            netAmount = netAmount,
            additionalAmount = additionalAmount,
            discountAmount = discountAmount,
            receiptDate = incomeDTO.receiptDate,
            referenceDate = referenceDate,
            description = incomeDTO.description,
            accountId = incomeDTO.accountId,
            sourceName = incomeDTO.sourceName.toUpperCase(),
            discounts = incomeDTO.discounts.map { toDocument(it) },
            additions = incomeDTO.additions.map { toDocument(it) },
            creationDate = LocalDateTime.now()
    )

    fun toDTO(
            document: Income
    ): IncomeResponse = IncomeResponse(
            incomeId = document.incomeId,
            grossAmount = document.grossAmount,
            netAmount = document.netAmount,
            additionalAmount = document.additionalAmount,
            description = document.description,
            receiptDate = document.receiptDate,
            referenceDate = document.referenceDate,
            sourceName = document.sourceName,
            discountAmount = document.discountAmount,
            discounts = document.discounts.map(::toDiscountDTO),
            additions = document.additions.map(::toAdditionDTO),
            creationDate = document.creationDate
    )

    fun toMonoDTO(income: Income): Mono<IncomeResponse> = just(toDTO(income))

    private fun toAdditionDTO(addition: Addition) = AdditionDTO(addition.amount, addition.description)

    private fun toDiscountDTO(discount: Discount) = DiscountDTO(discount.amount, discount.description)

    private fun toDocument(addition: AdditionDTO) = Addition(addition.amount!!, addition.description!!)

    private fun toDocument(discount: DiscountDTO) = Discount(discount.amount!!, discount.description!!)

}

