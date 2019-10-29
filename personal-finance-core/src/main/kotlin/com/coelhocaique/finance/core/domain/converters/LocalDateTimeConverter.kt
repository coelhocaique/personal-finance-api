package com.coelhocaique.finance.core.domain.converters

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME

class LocalDateTimeConverter: DynamoDBTypeConverter<String, LocalDateTime> {

    override fun convert(date: LocalDateTime?) = date?.format(ISO_LOCAL_DATE_TIME)

    override fun unconvert(date: String?): LocalDateTime = LocalDateTime.parse(date, ISO_LOCAL_DATE_TIME)

}
