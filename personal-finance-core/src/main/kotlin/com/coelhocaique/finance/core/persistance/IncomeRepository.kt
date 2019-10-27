package com.coelhocaique.finance.core.persistance

import com.coelhocaique.finance.core.domain.Income
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface IncomeRepository: ReactiveMongoRepository<Income, String>