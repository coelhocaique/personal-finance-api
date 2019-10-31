package com.coelhocaique.finance.core.persistance

import com.coelhocaique.finance.core.domain.Parameter
import org.socialsignin.spring.data.dynamodb.repository.DynamoDBCrudRepository
import org.socialsignin.spring.data.dynamodb.repository.EnableScan

@EnableScan
interface ParameterRepository: DynamoDBCrudRepository<Parameter, String> {

}