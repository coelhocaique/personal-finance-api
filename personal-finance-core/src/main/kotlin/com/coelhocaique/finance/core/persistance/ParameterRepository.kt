package com.coelhocaique.finance.core.persistance

import com.coelhocaique.finance.core.domain.Parameter
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import reactor.core.publisher.Mono.justOrEmpty
import java.util.*

@Component
class ParameterRepository(val repository: DynamoRepository) {

    private companion object Const {
        const val TABLE_NAME = "parameter"
        const val REFERENCE_DATE = "reference_date"
        const val DATE_FROM = "date_from"
        const val DATE_TO = "date_to"
        const val ID = "parameter_id"
        const val ACCOUNT_ID = "account_id"
        const val NAME = "parameter_name"
    }

    fun insert(document: Parameter): Mono<Parameter> {
        repository.addItem(TABLE_NAME, document)
        return just(document)
    }

    fun findByReferenceDateBetween(dateFrom: String, dateTo: String, accountId: UUID):
            Mono<List<Parameter>> {
        return justOrEmpty(repository.scanItemsBetween (
                TABLE_NAME,
                REFERENCE_DATE,
                mapOf(DATE_FROM to dateFrom, DATE_TO to dateTo),
                mapOf(ACCOUNT_ID to accountId),
                Parameter::class.java))
                .map { it.sortedByDescending { itt -> itt.creationDate } }
    }

    fun findByNameAndReferenceDateBetween(name: String, dateFrom: String, dateTo: String, accountId: UUID):
            Mono<List<Parameter>> {
        return justOrEmpty(repository.scanItemsBetween (
                TABLE_NAME,
                REFERENCE_DATE,
                mapOf(DATE_FROM to dateFrom, DATE_TO to dateTo),
                mapOf(NAME to name, ACCOUNT_ID to accountId),
                Parameter::class.java))
                .map { it.sortedByDescending { itt -> itt.creationDate } }
    }

    fun findByReferenceDate(referenceDate: String, accountId: UUID):
            Mono<List<Parameter>> {
        return scan(mapOf(REFERENCE_DATE to referenceDate, ACCOUNT_ID to accountId))
    }

    fun findByNameAndReferenceDate(name: String, referenceDate: String, accountId: UUID):
            Mono<List<Parameter>> {
        return scan(mapOf(NAME to name, REFERENCE_DATE to referenceDate, ACCOUNT_ID to accountId))
    }

    fun findByName(name: String, accountId: UUID):
            Mono<List<Parameter>> {
        return scan(mapOf(NAME to name, ACCOUNT_ID to accountId))
    }

    fun findById(id: UUID, accountId: UUID): Mono<Parameter> {
        return scan(mapOf(ID to id.toString(), ACCOUNT_ID to accountId))
                .flatMap { justOrEmpty(it.firstOrNull()) }
    }

    fun deleteById(id: UUID) {
        repository.deleteItem(TABLE_NAME, mapOf(ID to id))
    }

    private fun scan(keys: Map<String, Any>): Mono<List<Parameter>> {
        return justOrEmpty(repository.scanItems(TABLE_NAME, keys, Parameter::class.java))
                .map { it.sortedByDescending { itt -> itt.creationDate } }
    }
}