package com.coelhocaique.finance.core.persistance

import com.coelhocaique.finance.core.domain.RecurringDebt
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import reactor.core.publisher.Mono.justOrEmpty
import java.util.*

@Component
class RecurringDebtRepository(val repository: DynamoRepository) {

    private companion object Const {
        const val TABLE_NAME = "recurring_debt"
        const val ID = "recurring_debt_id"
        const val ACCOUNT_ID = "account_id"
    }
    
    fun insert(document: RecurringDebt): Mono<RecurringDebt> {
        repository.addItem(TABLE_NAME, document)
        return just(document)
    }

    fun findAll(accountId: UUID): Mono<List<RecurringDebt>> {
        return scan(mapOf(ACCOUNT_ID to accountId))
    }

    fun findAll(): List<RecurringDebt> {
        return repository.scanAll(TABLE_NAME, RecurringDebt::class.java)
    }

    fun findById(id: UUID, accountId: UUID): Mono<RecurringDebt> {
        return scan(mapOf(ID to id, ACCOUNT_ID to accountId))
                .flatMap { justOrEmpty(it.firstOrNull()) }
    }

    fun deleteById(id: UUID) {
        repository.deleteItem(TABLE_NAME, mapOf(ID to id))
    }

    private fun scan(keys: Map<String, Any>): Mono<List<RecurringDebt>> {
        return justOrEmpty(repository.scanItems(TABLE_NAME, keys, RecurringDebt::class.java))
                .map { it.sortedByDescending { itt -> itt.creationDate } }
    }
}