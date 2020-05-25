package com.coelhocaique.finance.core.scheduler

import com.coelhocaique.finance.core.domain.mapper.RecurringDebtMapper.toDebtDTO
import com.coelhocaique.finance.core.persistance.RecurringDebtRepository
import com.coelhocaique.finance.core.service.DebtService
import com.coelhocaique.finance.core.util.logger
import org.joda.time.LocalDateTime
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono.just

@Component
@EnableScheduling
class RecurringDebtScheduler(
        val recurringDebtRepository: RecurringDebtRepository,
        val debtService: DebtService
) {

    @Scheduled(cron = "0 0 0 1 * *")
    fun createRecurringDebts(){
        logger().info("M=createRecurringDebts, date=${LocalDateTime.now()}, stage=init")

        recurringDebtRepository.findAll()
                .map(::toDebtDTO)
                .map { d -> debtService.create(just(d)) }
                .forEach { pipeline -> pipeline.subscribe() }

        logger().info("M=createRecurringDebts, date=${LocalDateTime.now()}, stage=success")
    }
}