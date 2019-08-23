package com.coelhocaique.finance.core.repository

import com.coelhocaique.finance.core.document.Addition
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface AdditionRepository: ReactiveMongoRepository<Addition, String>