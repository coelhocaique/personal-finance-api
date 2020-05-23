package com.coelhocaique.finance.core.service.helper

import com.coelhocaique.finance.core.domain.Debt
import com.coelhocaique.finance.core.domain.dto.DebtDTO
import com.coelhocaique.finance.core.domain.mapper.DebtMapper
import com.coelhocaique.finance.core.util.generateReferenceDate
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.util.*

object DebtHelper {

    fun generateDebts(dto: DebtDTO): List<Debt>{
        val totalAmount = dto.amount
        val referenceCode = UUID.randomUUID()
        val debtDate = getFirstDebtDate(dto)
        val debtDates = getDebtDates(debtDate, dto.installments, listOf(debtDate))
        val installmentAmount = totalAmount.divide(BigDecimal(dto.installments),2, RoundingMode.HALF_UP)
        val newDto = dto.copy(totalAmount = totalAmount, referenceCode = referenceCode)

        return buildDebts(newDto, installmentAmount, totalAmount, debtDates)
    }

    private fun getFirstDebtDate(dto: DebtDTO): LocalDate {
        return if (dto.nextMonth!!)
            dto.debtDate.plusMonths(1)
        else
            dto.debtDate
    }

    private fun getDebtDates(debtDate: LocalDate, installments: Int,
                             debtDates: List<LocalDate>): List<LocalDate> {
        if (installments == 1)
            return debtDates
        val newDebtDate = debtDate.plusMonths(1)
        return getDebtDates(newDebtDate, installments - 1, debtDates + listOf(newDebtDate))
    }

    private fun buildDebts(dto: DebtDTO, installmentAmount: BigDecimal,
                           remainingAmount: BigDecimal, debtDates: List<LocalDate>,
                           debts: List<Debt> = listOf()): List<Debt> {

        if (debtDates.isEmpty())
            return debts

        val date = debtDates.first()
        val newDto = dto.copy(
                referenceDate = generateReferenceDate(date),
                amount = if (debtDates.size == 1) remainingAmount else installmentAmount,
                debtDate = date,
                installmentNumber = dto.installments - debtDates.size + 1)

        val newDebt = listOf(DebtMapper.toDocument(newDto));

        return buildDebts(dto, installmentAmount,
                remainingAmount.subtract(installmentAmount),
                debtDates.drop(1), debts + newDebt)
    }
}