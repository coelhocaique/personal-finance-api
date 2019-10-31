package com.coelhocaique.finance.core.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.LocalDate

fun <T : Any> T.logger(): Logger = LoggerFactory.getLogger(javaClass)

fun generateReferenceDate(date: LocalDate) = date.toString()
        .replace("-", "")
        .substring(0, 6)