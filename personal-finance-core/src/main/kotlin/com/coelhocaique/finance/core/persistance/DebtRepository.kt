package com.coelhocaique.finance.core.persistance

import com.coelhocaique.finance.core.domain.Debt
import org.socialsignin.spring.data.dynamodb.repository.DynamoDBCrudRepository
import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import java.util.Optional

@EnableScan
interface DebtRepository: DynamoDBCrudRepository<Debt, String> {

    fun findByReferenceCodeAndUserId(referenceCode: String, userId: String): List<Debt>

    fun findByReferenceDateBetweenAndUserId(from: String, to: String, userId: String): List<Debt>

    fun findByReferenceDateAndUserId(referenceDate: String, userId: String): List<Debt>

    fun findByIdAndUserId(referenceDate: String, userId: String): Optional<Debt>
}