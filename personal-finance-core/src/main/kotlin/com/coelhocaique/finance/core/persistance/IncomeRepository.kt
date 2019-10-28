package com.coelhocaique.finance.core.persistance

import com.coelhocaique.finance.core.domain.Income
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface IncomeRepository: ReactiveMongoRepository<Income, String> {

    @Query("{ \$and:[ {'referenceDate': { \$gte:?0 , \$lte:?1 } }]}")
    fun findByReferenceDateRange(from: String, to: String): Flux<Income>

    fun findByReferenceDate(referenceDate: String): Flux<Income>
}