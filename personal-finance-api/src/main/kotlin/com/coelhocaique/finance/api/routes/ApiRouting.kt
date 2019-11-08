package com.coelhocaique.finance.api.routes

import com.coelhocaique.finance.api.handler.CustomAttributeHandler
import com.coelhocaique.finance.api.handler.DebtHandler
import com.coelhocaique.finance.api.handler.IncomeHandler
import com.coelhocaique.finance.api.handler.ParameterHandler
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.router

@Component
class ApiRouting {

    @Bean
    fun incomeRoutes(handler: IncomeHandler) = router {
        POST("/v1/income", handler::create)
        GET("/v1/income", handler::fetchIncomes)
        GET("/v1/income/{id}", handler::findById)
        DELETE("/v1/income/{id}", handler::deleteById)
    }

    @Bean
    fun debtRoutes(handler: DebtHandler) = router {
        POST("/v1/debt", handler::create)
        GET("/v1/debt", handler::fetchDebts)
        GET("/v1/debt/{id}", handler::findById)
        DELETE("/v1/debt/{id}", handler::deleteById)
        DELETE("/v1/debt", handler::delete)
    }

    @Bean
    fun parameterRoutes(handler: ParameterHandler) = router {
        POST("/v1/parameter", handler::create)
        GET("/v1/parameter/{id}", handler::findById)
        GET("/v1/parameter", handler::fetchParameters)
        DELETE("/v1/parameter/{id}", handler::deleteById)
    }
    @Bean
    fun customAttributesRoutes(handler: CustomAttributeHandler) = router {
        POST("/v1/custom-attribute", handler::create)
        GET("/v1/custom-attribute", handler::fetchCustomAttributes)
        DELETE("/v1/custom-attribute/{id}", handler::deleteById)
    }

}