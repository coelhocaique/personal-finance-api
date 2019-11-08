package com.coelhocaique.finance.core.persistance

import com.coelhocaique.finance.core.domain.CustomAttribute
import org.socialsignin.spring.data.dynamodb.repository.DynamoDBCrudRepository
import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import java.util.Optional

@EnableScan
interface CustomAttributeRepository: DynamoDBCrudRepository<CustomAttribute, String> {

    fun findByPropertyNameAndAccountId(propertyName: String, accountId: String): List<CustomAttribute>

    fun findByAccountId(accountId: String): List<CustomAttribute>

    fun findByIdAndAccountId(id: String, accountId: String): Optional<CustomAttribute>

    fun deleteByIdAndAccountId(id: String, accountId: String)
}