package com.coelhocaique.finance.api.helper

import com.coelhocaique.finance.core.domain.dto.*
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import java.util.stream.Collectors

object LinkBuilder {

    fun buildForIncomes(uri: String, dtos: List<IncomeResponse>): Mono<List<IncomeResponse>> {
        return just(dtos.stream().map { buildForIncome(uri, it) }.collect(Collectors.toList()))
    }

    fun buildForIncome(uri: String, dto: IncomeResponse): IncomeResponse {
        val baseUri = uri.substringBefore("v1")
        val links = listOf(
                mapLink("GET", baseUri.plus("v1/income/".plus(dto.incomeId))),
                mapLink("GET", baseUri.plus("v1/income?reference_date=".plus(dto.referenceDate))),
                mapLink("DELETE",baseUri.plus("v1/income/".plus(dto.incomeId)))
        )

        return dto.copy(links = links)
    }

    fun buildForDebts(uri: String, dtos: List<DebtResponse>): Mono<List<DebtResponse>> {
        return just(dtos.stream().map { buildForDebt(uri, it) }.collect(Collectors.toList()))
    }

    fun buildForDebt(uri: String, dto: DebtResponse): DebtResponse {
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

    fun buildForParameters(uri: String, dtos: List<ParameterResponse>): Mono<List<ParameterResponse>> {
        return just(dtos.stream().map { buildForParameter(uri, it) }.collect(Collectors.toList()))
    }

    fun buildForParameter(uri: String, dto: ParameterResponse): ParameterResponse {
        val baseUri = uri.substringBefore("v1")
        val links = listOf(
                mapLink("GET", baseUri.plus("v1/parameter/".plus(dto.parameterId))),
                mapLink("GET", baseUri.plus("v1/parameter")),
                mapLink("DELETE",baseUri.plus("v1/parameter/".plus(dto.parameterId)))
        )

        return dto.copy(links = links)
    }

    fun buildForCustomAttributes(uri: String, requests: List<CustomAttributeResponse>): Mono<List<CustomAttributeResponse>> {
        return just(requests.stream().map { buildForCustomAttribute(uri, it) }.collect(Collectors.toList()))
    }

    fun buildForCustomAttribute(uri: String, request: CustomAttributeResponse): CustomAttributeResponse {
        val baseUri = uri.substringBefore("v1")
        val links = listOf(
                mapLink("GET", baseUri.plus("v1/custom-attribute")),
                mapLink("GET", baseUri.plus("v1/custom-attribute?property_name=".plus(request.propertyName))),
                mapLink("DELETE",baseUri.plus("v1/custom-attribute/".plus(request.customAttributeId)))
        )

        return request.copy(links = links)
    }

    fun buildForRecurringDebts(uri: String, dtos: List<RecurringDebtResponse>): Mono<List<RecurringDebtResponse>> {
        return just(dtos.stream().map { buildForRecurringDebt(uri, it) }.collect(Collectors.toList()))
    }

    fun buildForRecurringDebt(uri: String, dto: RecurringDebtResponse): RecurringDebtResponse {
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