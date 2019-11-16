package com.coelhocaique.finance.core.persistance

import com.coelhocaique.finance.core.domain.Income
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.justOrEmpty

@Component
class IncomeRepository(val repository: DynamoRepository) {

    private companion object Const {
        const val TABLE_NAME = "income"
        const val REFERENCE_DATE = "reference_date"
        const val DATE_FROM = "date_from"
        const val DATE_TO = "date_to"
        const val ID = "income_id"
        const val ACCOUNT_ID = "account_id"
    }

    fun insert(document: Income): Mono<Income> {
        repository.addItem(TABLE_NAME, document)
        return Mono.just(document)
    }

    fun findByReferenceDateBetween(dateFrom: String, dateTo: String, accountId: String):
            Mono<List<Income>> {
        return justOrEmpty(repository.scanItemsBetween(
                TABLE_NAME,
                REFERENCE_DATE,
                mapOf(DATE_FROM to dateFrom, DATE_TO to dateTo),
                mapOf(ACCOUNT_ID to accountId),
                Income::class.java))
    }

    fun findByReferenceDate(referenceDate: String, accountId: String):
            Mono<List<Income>> {
        return scan(mapOf(REFERENCE_DATE to referenceDate, ACCOUNT_ID to accountId))
    }

    fun findById(id: String, accountId: String): Mono<Income> {
        return scan(mapOf(ID to id, ACCOUNT_ID to accountId))
                .flatMap { justOrEmpty(it.firstOrNull()) }
    }

    fun deleteById(id: String) {
        repository.deleteItem(TABLE_NAME, mapOf(ID to id))
    }

    private fun scan(keys: Map<String, String>): Mono<List<Income>> {
        return justOrEmpty(repository.scanItems(TABLE_NAME, keys, Income::class.java))
    }
}