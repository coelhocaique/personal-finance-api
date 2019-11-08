package com.coelhocaique.finance.core.persistance

import com.coelhocaique.finance.core.domain.Debt
import org.socialsignin.spring.data.dynamodb.repository.DynamoDBCrudRepository
import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import java.util.Optional

@EnableScan
interface DebtRepository: DynamoDBCrudRepository<Debt, String> {

    fun findByReferenceCodeAndAccountId(referenceCode: String, accountId: String): List<Debt>

    fun findByReferenceDateBetweenAndAccountId(dateFrom: String, dateTo: String, accountId: String): List<Debt>

    fun findByReferenceDateAndAccountId(referenceDate: String, accountId: String): List<Debt>

    fun findByIdAndAccountId(id: String, accountId: String): Optional<Debt>

    fun deleteByReferenceCodeAndAccountId(referenceCode: String, accountId: String)

    fun deleteByIdAndAccountId(id: String, accountId: String)
}