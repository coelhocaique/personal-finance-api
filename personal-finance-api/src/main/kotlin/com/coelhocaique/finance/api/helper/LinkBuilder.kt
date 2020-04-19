package com.coelhocaique.finance.api.helper

import com.coelhocaique.finance.core.domain.dto.*
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import java.util.stream.Collectors

object LinkBuilder {

    fun buildForIncomes(uri: String, dtos: List<IncomeDTO>): Mono<List<IncomeDTO>> {
        return just(dtos.stream().map { buildForIncome(uri, it) }.collect(Collectors.toList()))
    }

    fun buildForIncome(uri: String, dto: IncomeDTO): IncomeDTO {
        val baseUri = uri.substringBefore("v1")
        val links = listOf(
                mapLink("GET", baseUri.plus("v1/income/".plus(dto.incomeId))),
                mapLink("GET", baseUri.plus("v1/income?reference_date=".plus(dto.referenceDate))),
                mapLink("DELETE",baseUri.plus("v1/income/".plus(dto.incomeId)))
        )

        return dto.copy(links = links)
    }

    fun buildForDebts(uri: String, dtos: List<DebtDTO>): Mono<List<DebtDTO>> {
        return just(dtos.stream().map { buildForDebt(uri, it) }.collect(Collectors.toList()))
    }

    fun buildForDebt(uri: String, dto: DebtDTO): DebtDTO {
        val baseUri = uri.substringBefore("v1")
        val links = listOf(
                mapLink("GET", baseUri.plus("v1/debt/".plus(dto.debtId))),
                mapLink("GET", baseUri.plus("v1/debt?reference_date=".plus(dto.referenceDate))),
                mapLink("GET", baseUri.plus("v1/debt?reference_code=".plus(dto.referenceCode))),
                mapLink("DELETE",baseUri.plus("v1/debt/".plus(dto.debtId))),
                mapLink("DELETE", baseUri.plus("v1/debt?reference_code=".plus(dto.referenceCode)))
        )

        return dto.copy(links = links)
    }

    fun buildForParameters(uri: String, dtos: List<ParameterDTO>): Mono<List<ParameterDTO>> {
        return just(dtos.stream().map { buildForParameter(uri, it) }.collect(Collectors.toList()))
    }

    fun buildForParameter(uri: String, dto: ParameterDTO): ParameterDTO {
        val baseUri = uri.substringBefore("v1")
        val links = listOf(
                mapLink("GET", baseUri.plus("v1/parameter/".plus(dto.parameterId))),
                mapLink("GET", baseUri.plus("v1/parameter")),
                mapLink("DELETE",baseUri.plus("v1/parameter/".plus(dto.parameterId)))
        )

        return dto.copy(links = links)
    }

    fun buildForCustomAttributes(uri: String, dtos: List<CustomAttributeDTO>): Mono<List<CustomAttributeDTO>> {
        return just(dtos.stream().map { buildForCustomAttribute(uri, it) }.collect(Collectors.toList()))
    }

    fun buildForCustomAttribute(uri: String, dto: CustomAttributeDTO): CustomAttributeDTO {
        val baseUri = uri.substringBefore("v1")
        val links = listOf(
                mapLink("GET", baseUri.plus("v1/custom-attribute")),
                mapLink("GET", baseUri.plus("v1/custom-attribute?property_name=".plus(dto.propertyName))),
                mapLink("DELETE",baseUri.plus("v1/custom-attribute/".plus(dto.customAttributeId)))
        )

        return dto.copy(links = links)
    }

    fun buildForRecurringDebts(uri: String, dtos: List<RecurringDebtDTO>): Mono<List<RecurringDebtDTO>> {
        return just(dtos.stream().map { buildForRecurringDebt(uri, it) }.collect(Collectors.toList()))
    }

    fun buildForRecurringDebt(uri: String, dto: RecurringDebtDTO): RecurringDebtDTO {
        val baseUri = uri.substringBefore("v1")
        val links = listOf(
                mapLink("GET", baseUri.plus("v1/recurring-debt")),
                mapLink("GET", baseUri.plus("v1/recurring-debt/".plus(dto.recurringDebtId))),
                mapLink("DELETE",baseUri.plus("v1/recurring-debt/".plus(dto.recurringDebtId)))
        )

        return dto.copy(links = links)
    }

    private fun mapLink(method: String, href: String) = mapOf("method" to method, "href" to href)
}