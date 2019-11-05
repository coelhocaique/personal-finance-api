package com.coelhocaique.finance.api.helper

import com.coelhocaique.finance.api.dto.DebtRequestDTO
import com.coelhocaique.finance.api.dto.IncomeRequestDTO
import com.coelhocaique.finance.api.dto.ParameterRequestDTO
import com.coelhocaique.finance.api.helper.Fields.AMOUNT
import com.coelhocaique.finance.api.helper.Fields.DEBT_DATE
import com.coelhocaique.finance.api.helper.Fields.DESCRIPTION
import com.coelhocaique.finance.api.helper.Fields.GROSS_AMOUNT
import com.coelhocaique.finance.api.helper.Fields.INSTALLMENTS
import com.coelhocaique.finance.api.helper.Fields.NAME
import com.coelhocaique.finance.api.helper.Fields.RECEIPT_DATE
import com.coelhocaique.finance.api.helper.Fields.REF_DATE
import com.coelhocaique.finance.api.helper.Fields.SOURCE_NAME
import com.coelhocaique.finance.api.helper.Fields.TAG
import com.coelhocaique.finance.api.helper.Fields.TYPE
import com.coelhocaique.finance.api.helper.Fields.VALUE
import com.coelhocaique.finance.api.helper.Messages.NOT_NULL
import com.coelhocaique.finance.api.helper.exception.ApiException
import com.coelhocaique.finance.api.helper.exception.ApiException.ApiExceptionHelper.business
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.error
import reactor.core.publisher.Mono.just
import java.lang.IllegalArgumentException
import java.util.Objects.isNull

object RequestValidator {

    fun validate(dto: DebtRequestDTO): Mono<DebtRequestDTO> {
        return try {
            nonNull(dto.amount, AMOUNT)
            nonNull(dto.debtDate, DEBT_DATE)
            nonNull(dto.description, DESCRIPTION)
            nonNull(dto.installments, INSTALLMENTS)
            nonNull(dto.tag, TAG)
            nonNull(dto.type, TYPE)
            just(dto)
        } catch (e: IllegalArgumentException){
            error(business(e.message!!))
        }
    }

    fun validate(dto: IncomeRequestDTO): Mono<IncomeRequestDTO> {
        return try {
            nonNull(dto.grossAmount, GROSS_AMOUNT)
            nonNull(dto.receiptDate, RECEIPT_DATE)
            nonNull(dto.description, DESCRIPTION)
            nonNull(dto.referenceDate, REF_DATE)
            nonNull(dto.sourceName, SOURCE_NAME)
            dto.discounts.forEach {
                nonNull(it.amount, AMOUNT)
                nonNull(it.description, DESCRIPTION)
            }
            dto.additions.forEach {
                nonNull(it.amount, AMOUNT)
                nonNull(it.description, DESCRIPTION)
            }
            just(dto)
        } catch (e: IllegalArgumentException){
            error(business(e.message!!))
        }
    }

    fun validate(dto: ParameterRequestDTO): Mono<ParameterRequestDTO> {
        return try {
            nonNull(dto.name, NAME)
            nonNull(dto.value, VALUE)
            nonNull(dto.referenceDate, REF_DATE)
            just(dto)
        } catch (e: IllegalArgumentException){
            error(business(e.message!!))
        }
    }

    private fun nonNull(o: Any?, attr: String) = requireNotNull(o, { NOT_NULL.format(attr)})

}