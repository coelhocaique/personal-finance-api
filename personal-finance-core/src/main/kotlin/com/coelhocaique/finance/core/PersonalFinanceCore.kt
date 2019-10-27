package com.coelhocaique.finance.core

import com.coelhocaique.finance.core.persistance.IncomeRepository
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@Configuration
@ComponentScan(basePackageClasses = [PersonalFinanceCore::class])
@PropertySource("classpath:application.yml")
@EnableReactiveMongoRepositories(basePackageClasses = [IncomeRepository::class])
class PersonalFinanceCore