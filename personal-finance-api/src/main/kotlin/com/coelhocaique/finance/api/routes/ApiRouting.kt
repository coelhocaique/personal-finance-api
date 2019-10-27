package com.coelhocaique.finance.api.routes

import com.coelhocaique.finance.api.handler.DebtHandler
import com.coelhocaique.finance.api.handler.IncomeHandler
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.router

@Component
class ApiRouting {

    @Bean
    fun incomeRoutes(handler: IncomeHandler) = router {
        GET("/v1/income/{id}", handler::findById)
        POST("/v1/income", handler::create)
        DELETE("/v1/income/{id}", handler::delete)
    }

    @Bean
    fun debtRoutes(handler: DebtHandler) = router {
        POST("/v1/debt", handler::create)
        GET("/v1/debt/{id}", handler::findById)
        GET("/v1/debt", handler::fetchDebts)
    }
}