package com.coelhocaique.finance.api.routes

import com.coelhocaique.finance.api.handler.IncomeHandler
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.router

@Component
class IncomeRouter {

    @Bean
    fun incomeRoutes(handler: IncomeHandler) = router {
        GET("/income", handler::findById)
        POST("/income", handler::create)
        DELETE("/income", handler::find)
    }

}