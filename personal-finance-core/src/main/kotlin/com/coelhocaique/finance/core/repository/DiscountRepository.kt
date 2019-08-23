package com.coelhocaique.finance.core.repository

import com.coelhocaique.finance.core.document.Discount
import com.coelhocaique.finance.core.document.Income
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface DiscountRepository: ReactiveMongoRepository<Discount, String>