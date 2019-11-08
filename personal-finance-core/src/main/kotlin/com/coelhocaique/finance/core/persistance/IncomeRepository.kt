package com.coelhocaique.finance.core.persistance

import com.coelhocaique.finance.core.domain.Income
import org.socialsignin.spring.data.dynamodb.repository.DynamoDBCrudRepository
import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import java.util.Optional

@EnableScan
interface IncomeRepository: DynamoDBCrudRepository<Income, String> {

    fun findByReferenceDateBetweenAndAccountId(from: String, to: String, accountId: String): List<Income>

    fun findByReferenceDateAndAccountId(referenceDate: String, accountId: String): List<Income>

    fun findByIdAndAccountId(id: String, accountId: String): Optional<Income>

    fun deleteByIdAndAccountId(id: String, accountId: String)
}