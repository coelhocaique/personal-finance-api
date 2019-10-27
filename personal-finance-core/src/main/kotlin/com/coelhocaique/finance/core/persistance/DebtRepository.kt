package com.coelhocaique.finance.core.persistance

import com.coelhocaique.finance.core.domain.Debt
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface DebtRepository: ReactiveMongoRepository<Debt, String> {

    fun findByReferenceCode(referenceCode: String): Flux<Debt>

    @Query("{ \$and:[ {'referenceDate': { \$gte:?0 , \$lte:?1 } }]}")
    fun findByReferenceDateRange(from: String, to: String): Flux<Debt>

    fun findByReferenceDate(referenceDate: String): Flux<Debt>

}