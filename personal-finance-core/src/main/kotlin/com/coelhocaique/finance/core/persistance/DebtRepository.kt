package com.coelhocaique.finance.core.persistance

import com.coelhocaique.finance.core.domain.Debt
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import reactor.core.publisher.Mono.justOrEmpty

@Component
class DebtRepository(val repository: DynamoRepository) {

    private companion object Const {
        const val TABLE_NAME = "debt"
        const val REFERENCE_DATE = "reference_date"
        const val REFERENCE_CODE = "reference_code"
        const val DATE_FROM = "date_from"
        const val DATE_TO = "date_to"
        const val ID = "debt_id"
        const val ACCOUNT_ID = "account_id"
    }

    fun insertAll(documents: List<Debt>): Mono<List<Debt>> {
        documents.forEach { insert(it) }
        return just(documents)
    }
    
    fun insert(document: Debt): Mono<Debt> {
        repository.addItem(TABLE_NAME, document)
        return just(document)
    }

    fun findByReferenceDateBetween(dateFrom: String, dateTo: String, accountId: String):
            Mono<List<Debt>> {
        return justOrEmpty(repository.scanItemsBetween(
                TABLE_NAME,
                REFERENCE_DATE,
                mapOf(DATE_FROM to dateFrom, DATE_TO to dateTo),
                mapOf(ACCOUNT_ID to accountId),
                Debt::class.java))
                .map { it.sortedByDescending { itt -> itt.creationDate } }
    }

    fun findByReferenceDate(referenceDate: String, accountId: String):
            Mono<List<Debt>> {
        return scan(mapOf(REFERENCE_DATE to referenceDate, ACCOUNT_ID to accountId))
    }

    fun findByReferenceCode(referenceCode: String, accountId: String):
            Mono<List<Debt>> {
        return scan(mapOf(REFERENCE_CODE to referenceCode, ACCOUNT_ID to accountId))
    }

    fun findById(id: String, accountId: String): Mono<Debt> {
        return scan(mapOf(ID to id, ACCOUNT_ID to accountId))
                .flatMap { justOrEmpty(it.firstOrNull()) }
    }

    fun deleteById(id: String) {
        repository.deleteItem(TABLE_NAME, mapOf(ID to id))
    }

    private fun scan(keys: Map<String, String>): Mono<List<Debt>> {
        return justOrEmpty(repository.scanItems(TABLE_NAME, keys, Debt::class.java))
                .map { it.sortedByDescending { itt -> itt.creationDate } }
    }
}