package com.coelhocaique.finance.core.service

import com.coelhocaique.finance.core.document.Debt
import com.coelhocaique.finance.core.dto.DebtDTO
import com.coelhocaique.finance.core.mapper.DebtMapper
import com.coelhocaique.finance.core.repository.DebtRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@Service
class DebtService(private val repository: DebtRepository) {

    fun create(dto: Mono<DebtDTO>): Mono<List<DebtDTO>> {
        return dto.map(::transform)
                .flatMap { repository.insert(it).collectList()}
                .map { it.map { itt -> DebtMapper.toDTO(itt) } }
    }

    private fun transform(dto: DebtDTO): List<Debt>{
        val totalAmount = dto.amount
        val referenceCode = UUID.randomUUID()

        val debtDate = if (dto.nextMonth!!)
            dto.debtDate.plusMonths(1)
        else
            dto.debtDate

        val debtDates = getDebtDates(debtDate, dto.installments, listOf(debtDate))
        val installmentAmount = totalAmount.divide(BigDecimal(dto.installments),2, BigDecimal.ROUND_HALF_UP)

        return buildDebts(dto.copy(totalAmount = totalAmount, referenceCode = referenceCode),
                installmentAmount,
                totalAmount, debtDates)
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

    private fun generateReferenceDate(date: LocalDate) =
            date.toString().replace("-", "").substring(0, 6)

}