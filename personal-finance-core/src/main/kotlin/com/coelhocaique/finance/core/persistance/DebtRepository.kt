package com.coelhocaique.finance.core.persistance

import com.coelhocaique.finance.core.domain.Debt
import org.socialsignin.spring.data.dynamodb.repository.DynamoDBCrudRepository
import org.socialsignin.spring.data.dynamodb.repository.EnableScan

@EnableScan
interface DebtRepository: DynamoDBCrudRepository<Debt, String> {

    fun findByReferenceCode(referenceCode: String): List<Debt>

    fun findByReferenceDateBetween(from: String, to: String): List<Debt>

    fun findByReferenceDate(referenceDate: String): List<Debt>

}