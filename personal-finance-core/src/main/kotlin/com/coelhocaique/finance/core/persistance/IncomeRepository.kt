package com.coelhocaique.finance.core.persistance

import com.coelhocaique.finance.core.domain.Income
import org.socialsignin.spring.data.dynamodb.repository.DynamoDBCrudRepository
import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import java.util.Optional

@EnableScan
interface IncomeRepository: DynamoDBCrudRepository<Income, String> {

    fun findByReferenceDateBetweenAndUserId(from: String, to: String, userId: String): List<Income>

    fun findByReferenceDateAndUserId(referenceDate: String, userId: String): List<Income>

    fun findByIdAndUserId(id: String, userId: String): Optional<Income>

    fun deleteByIdAndUserId(id: String, userId: String)
}