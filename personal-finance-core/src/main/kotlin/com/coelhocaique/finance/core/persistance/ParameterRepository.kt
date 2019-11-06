package com.coelhocaique.finance.core.persistance

import com.coelhocaique.finance.core.domain.Parameter
import org.socialsignin.spring.data.dynamodb.repository.DynamoDBCrudRepository
import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import java.util.Optional

@EnableScan
interface ParameterRepository: DynamoDBCrudRepository<Parameter, String> {

    fun findByReferenceDateBetweenAndUserId(dateFrom: String, dateTo: String, userId: String): List<Parameter>

    fun findByReferenceDateAndUserId(referenceDate: String, userId: String): List<Parameter>

    fun findByIdAndUserId(id: String, userId: String): Optional<Parameter>

    fun deleteByIdAndUserId(id: String, userId: String)
}