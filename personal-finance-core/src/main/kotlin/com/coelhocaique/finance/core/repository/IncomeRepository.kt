package com.coelhocaique.finance.core.repository

import com.coelhocaique.finance.core.document.Income
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface IncomeRepository: ReactiveMongoRepository<Income, String>