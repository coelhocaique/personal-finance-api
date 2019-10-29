package com.coelhocaique.finance.core.domain.converters

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE

class LocalDateConverter: DynamoDBTypeConverter<String, LocalDate> {

    override fun convert(date: LocalDate?) = date?.format(ISO_LOCAL_DATE)

    override fun unconvert(date: String?): LocalDate = LocalDate.parse(date, ISO_LOCAL_DATE)

}
