package com.coelhocaique.finance.core.persistance

import com.coelhocaique.finance.core.domain.CustomAttribute
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import reactor.core.publisher.Mono.justOrEmpty
import java.util.*

@Component
class CustomAttributeRepository(val repository: DynamoRepository) {

    private companion object Const {
        const val TABLE_NAME = "custom_attribute"
        const val PROPERTY_NAME = "property_name"
        const val ID = "custom_attribute_id"
        const val ACCOUNT_ID = "account_id"
    }

    fun insert(document: CustomAttribute): Mono<CustomAttribute> {
        repository.addItem(TABLE_NAME, document)
        return just(document)
    }

    fun findByPropertyName(propertyName: String, accountId: UUID): Mono<List<CustomAttribute>> {
        return scan(mapOf(PROPERTY_NAME to propertyName, ACCOUNT_ID to accountId))
    }

    fun findAll(accountId: UUID): Mono<List<CustomAttribute>> {
        return scan(mapOf(ACCOUNT_ID to accountId))
    }

    fun findById(id: UUID, accountId: UUID): Mono<CustomAttribute> {
        return scan(mapOf(ID to id, ACCOUNT_ID to accountId))
                .flatMap { justOrEmpty(it.firstOrNull()) }
    }

    fun deleteById(id: UUID) {
        repository.deleteItem(TABLE_NAME, mapOf(ID to id))
    }

    private fun scan(keys: Map<String, Any>): Mono<List<CustomAttribute>> {
        return justOrEmpty(repository.scanItems(TABLE_NAME, keys, CustomAttribute::class.java))
                .map { it.sortedByDescending { itt -> itt.creationDate } }
    }
}