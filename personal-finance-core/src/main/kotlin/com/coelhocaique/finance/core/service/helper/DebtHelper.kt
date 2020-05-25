package com.coelhocaique.finance.core.service.helper

import com.coelhocaique.finance.core.domain.Debt
import com.coelhocaique.finance.core.domain.dto.DebtRequest
import com.coelhocaique.finance.core.domain.mapper.DebtMapper
import com.coelhocaique.finance.core.util.generateReferenceDate
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.util.*

object DebtHelper {

    fun generateDebts(
            dto: DebtRequest
    ): List<Debt> {
        val totalAmount = dto.amount
        val referenceCode = UUID.randomUUID()
        val debtDate = getFirstDebtDate(dto)
        val debtDates = getDebtDates(debtDate, dto.installments, listOf(debtDate))
        val installmentAmount = totalAmount.divide(BigDecimal(dto.installments), 2, RoundingMode.HALF_UP)

        return buildDebts(
                dto,
                totalAmount,
                installmentAmount,
                totalAmount,
                referenceCode,
                debtDates
        )
    }

    private fun getFirstDebtDate(
            dto: DebtRequest
    ): LocalDate {
        return if (dto.nextMonth)
            dto.debtDate.plusMonths(1)
        else
            dto.debtDate
    }

    private fun getDebtDates(
            debtDate: LocalDate,
            installments: Int,
            debtDates: List<LocalDate>
    ): List<LocalDate> {
        if (installments == 1)
            return debtDates

        val newDebtDate = debtDate.plusMonths(1)

        return getDebtDates(newDebtDate, installments - 1, debtDates + listOf(newDebtDate))
    }

    private fun buildDebts(
            dto: DebtRequest,
            totalAmount: BigDecimal,
            installmentAmount: BigDecimal,
            remainingAmount: BigDecimal,
            referenceCode: UUID,
            debtDates: List<LocalDate>,
            debts: List<Debt> = listOf()
    ): List<Debt> {

        if (debtDates.isEmpty())
            return debts

        val date = debtDates.first()
        val referenceDate = generateReferenceDate(date)
        val installmentNumber = dto.installments - debtDates.size + 1

        val newDto = dto.copy(
                amount = if (debtDates.size == 1) remainingAmount else installmentAmount,
                debtDate = date)

        val newDebt = DebtMapper.toDocument(
                newDto,
                referenceCode,
                installmentNumber,
                referenceDate,
                totalAmount
        )

        return buildDebts(dto,
                totalAmount,
                installmentAmount,
                remainingAmount.subtract(installmentAmount),
                referenceCode,
                debtDates.drop(1),
                debts + listOf(newDebt))
    }
}