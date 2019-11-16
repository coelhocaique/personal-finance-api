package com.coelhocaique.finance.core

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@ComponentScan(basePackageClasses = [PersonalFinanceCore::class])
@PropertySource(value=["classpath:core-application.properties"])
class PersonalFinanceCore