package com.coelhocaique.finance.api

import com.coelhocaique.finance.core.PersonalFinanceCore
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.PropertySource

@SpringBootApplication
@ComponentScan(basePackageClasses = [PersonalFinanceApiApplication::class])
@Import(PersonalFinanceCore::class)
@PropertySource(value=["classpath:api-application.properties"])
class PersonalFinanceApiApplication

fun main(args: Array<String>) {
	runApplication<PersonalFinanceApiApplication>(*args)
}
