package com.coelhocaique.finance.core.persistance

import com.coelhocaique.finance.core.domain.Income
import org.socialsignin.spring.data.dynamodb.repository.DynamoDBCrudRepository
import org.socialsignin.spring.data.dynamodb.repository.EnableScan

@EnableScan
interface IncomeRepository: DynamoDBCrudRepository<Income, String> {

    fun findByReferenceDateBetween(from: String, to: String): List<Income>

    fun findByReferenceDate(referenceDate: String): List<Income>
}