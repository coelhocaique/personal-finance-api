package com.coelhocaique.finance.core.domain.mapper

import com.coelhocaique.finance.core.domain.Income
import com.coelhocaique.finance.core.domain.dto.AdditionDTO
import com.coelhocaique.finance.core.domain.dto.DiscountDTO
import com.coelhocaique.finance.core.domain.dto.IncomeDTO
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import java.time.LocalDateTime
import java.util.*

object IncomeMapper {

    fun toDocument(incomeDTO: IncomeDTO): Income =
        Income( incomeId = UUID.randomUUID().toString(),
                grossAmount = incomeDTO.grossAmount.toString(),
                netAmount = incomeDTO.netAmount!!.toString(),
                additionalAmount = incomeDTO.additionalAmount.toString(),
                receiptDate = incomeDTO.receiptDate,
                referenceDate = incomeDTO.referenceDate,
                description = incomeDTO.description,
                username = incomeDTO.username,
                sourceName = incomeDTO.sourceName.toUpperCase(),
                discountAmount = incomeDTO.discountAmount.toString(),
                discounts = incomeDTO.discounts?.map{ toDocument(it)},
                additions = incomeDTO.additions?.map{ toDocument(it)},
                creationDate = LocalDateTime.now())

    fun toDTO(income: Income): IncomeDTO =
                IncomeDTO(
                        incomeId = UUID.fromString(income.incomeId),
                        grossAmount = income.grossAmount!!.toBigDecimal(),
                        netAmount = income.netAmount!!.toBigDecimal(),
                        additionalAmount = income.additionalAmount?.toBigDecimal(),
                        description = income.description!!,
                        username = income.username!!,
                        receiptDate = income.receiptDate!!,
                        referenceDate = income.referenceDate!!,
                        sourceName = income.sourceName!!,
                        discountAmount = income.discountAmount?.toBigDecimal(),
                        discounts = income.discounts?.map(::toDiscountDTO),
                        additions = income.additions?.map(::toAdditionDTO),
                        creationDate = income.creationDate)

    fun toMonoDTO(income: Income): Mono<IncomeDTO> = just(toDTO(income))

    private fun toAdditionDTO(addition: Map<String, String>) =
            AdditionDTO(addition["amount"]?.toBigDecimal()!!, addition["description"] ?:"")

    private fun toDiscountDTO(discount: Map<String, String>) =
            DiscountDTO(discount["amount"]?.toBigDecimal()!!, discount["description"] ?:"")

    fun toDocument(addition: AdditionDTO): Map<String, String> =
            mapOf("amount" to addition.amount.toString(), "description" to addition.description)

    fun toDocument(discount: DiscountDTO): Map<String, String> =
            mapOf("amount" to discount.amount.toString(), "description" to discount.description)
}

