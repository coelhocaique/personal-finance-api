package com.coelhocaique.finance.core.persistance

import com.coelhocaique.finance.core.domain.Parameter
import org.socialsignin.spring.data.dynamodb.repository.DynamoDBCrudRepository
import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import java.util.Optional

@EnableScan
interface ParameterRepository: DynamoDBCrudRepository<Parameter, String> {

    fun findByReferenceDateBetweenAndAccountId(dateFrom: String, dateTo: String, accountId: String): List<Parameter>

    fun findByReferenceDateAndAccountId(referenceDate: String, accountId: String): List<Parameter>

    fun findByIdAndAccountId(id: String, accountId: String): Optional<Parameter>

    fun deleteByIdAndAccountId(id: String, accountId: String)
}