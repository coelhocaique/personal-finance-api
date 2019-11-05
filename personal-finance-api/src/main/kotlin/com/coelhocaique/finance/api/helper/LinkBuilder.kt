package com.coelhocaique.finance.api.helper

import com.coelhocaique.finance.core.domain.dto.DebtDTO
import com.coelhocaique.finance.core.domain.dto.IncomeDTO
import com.coelhocaique.finance.core.domain.dto.ParameterDTO
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import java.util.stream.Collectors

object LinkBuilder {

    fun buildForIncome(uri: String, incomeDTO: IncomeDTO): IncomeDTO {
        val baseUri = uri.substringBefore("v1")
        val links = listOf(
                mapLink("GET", baseUri.plus("v1/income/".plus(incomeDTO.incomeId))),
                mapLink("GET", baseUri.plus("v1/income?reference_date=".plus(incomeDTO.referenceDate))),
                mapLink("DELETE",baseUri.plus("v1/income/".plus(incomeDTO.incomeId)))
        )

        return incomeDTO.copy(links = links)
    }

    fun buildForIncomes(uri: String, incomeDTOs: List<IncomeDTO>): Mono<List<IncomeDTO>> {
        return just(incomeDTOs.stream().map { buildForIncome(uri, it) }.collect(Collectors.toList()))
    }

    fun buildForDebts(uri: String, debtDTOs: List<DebtDTO>): Mono<List<DebtDTO>> {
        return just(debtDTOs.stream().map { buildForDebt(uri, it) }.collect(Collectors.toList()))
    }

    fun buildForDebt(uri: String, debtDTO: DebtDTO): DebtDTO {
        val baseUri = uri.substringBefore("v1")
        val links = listOf(
                mapLink("GET", baseUri.plus("v1/debt/".plus(debtDTO.debtId))),
                mapLink("GET", baseUri.plus("v1/debt?reference_date=".plus(debtDTO.referenceDate))),
                mapLink("GET", baseUri.plus("v1/debt?reference_code=".plus(debtDTO.referenceCode))),
                mapLink("DELETE",baseUri.plus("v1/debt/".plus(debtDTO.debtId)))
        )

        return debtDTO.copy(links = links)
    }

    fun buildForParameters(uri: String, parameterDTOs: List<ParameterDTO>): Mono<List<ParameterDTO>> {
        return just(parameterDTOs.stream().map { buildForParameter(uri, it) }.collect(Collectors.toList()))
    }

    fun buildForParameter(uri: String, parameterDTO: ParameterDTO): ParameterDTO {
        val baseUri = uri.substringBefore("v1")
        val links = listOf(
                mapLink("GET", baseUri.plus("v1/parameter/".plus(parameterDTO.parameterId))),
                mapLink("GET", baseUri.plus("v1/parameter")),
                mapLink("DELETE",baseUri.plus("v1/parameter/".plus(parameterDTO.parameterId)))
        )

        return parameterDTO.copy(links = links)
    }

    private fun mapLink(method: String, href: String) = mapOf("method" to method, "href" to href)
}