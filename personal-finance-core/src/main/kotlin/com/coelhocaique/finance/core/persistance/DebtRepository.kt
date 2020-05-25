package com.coelhocaique.finance.core.persistance

import com.coelhocaique.finance.core.domain.Debt
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import reactor.core.publisher.Mono.justOrEmpty
import java.util.*

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

    fun findByReferenceDateBetween(dateFrom: String, dateTo: String, accountId: UUID):
            Mono<List<Debt>> {
        return justOrEmpty(repository.scanItemsBetween(
                TABLE_NAME,
                REFERENCE_DATE,
                mapOf(DATE_FROM to dateFrom, DATE_TO to dateTo),
                mapOf(ACCOUNT_ID to accountId),
                Debt::class.java))
                .map { it.sortedByDescending { itt -> itt.creationDate } }
    }

    fun findByReferenceDate(referenceDate: String, accountId: UUID):
            Mono<List<Debt>> {
        return scan(mapOf(REFERENCE_DATE to referenceDate, ACCOUNT_ID to accountId))
    }

    fun findByReferenceCode(referenceCode: UUID, accountId: UUID):
            Mono<List<Debt>> {
        return scan(mapOf(REFERENCE_CODE to referenceCode, ACCOUNT_ID to accountId))
    }

    fun findById(id: UUID, accountId: UUID): Mono<Debt> {
        return scan(mapOf(ID to id, ACCOUNT_ID to accountId))
                .flatMap { justOrEmpty(it.firstOrNull()) }
    }

    fun deleteById(id: UUID) {
        repository.deleteItem(TABLE_NAME, mapOf(ID to id))
    }

    private fun scan(keys: Map<String, Any>): Mono<List<Debt>> {
        return justOrEmpty(repository.scanItems(TABLE_NAME, keys, Debt::class.java))
                .map { it.sortedByDescending { itt -> itt.creationDate } }
    }
}