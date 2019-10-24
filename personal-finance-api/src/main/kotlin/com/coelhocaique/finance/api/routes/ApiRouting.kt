package com.coelhocaique.finance.api.routes

import com.coelhocaique.finance.api.handler.IncomeHandler
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.router

@Component
class ApiRouting {

    @Bean
    fun incomeRoutes(handler: IncomeHandler) = router {
        GET("/income/{id}", handler::findById)
        POST("/income", handler::create)
        DELETE("/income/{id}", handler::delete)
    }

}