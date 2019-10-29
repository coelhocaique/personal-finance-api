package com.coelhocaique.finance.core

import com.coelhocaique.finance.core.persistance.IncomeRepository
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@ComponentScan(basePackageClasses = [PersonalFinanceCore::class])
@PropertySource("classpath:application.properties")
@EnableDynamoDBRepositories(basePackageClasses = [IncomeRepository::class])
class PersonalFinanceCore