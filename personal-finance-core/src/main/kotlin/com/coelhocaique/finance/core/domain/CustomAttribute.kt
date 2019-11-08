package com.coelhocaique.finance.core.domain

import com.amazonaws.services.dynamodbv2.datamodeling.*
import com.coelhocaique.finance.core.domain.converters.LocalDateTimeConverter
import org.springframework.data.annotation.Id
import java.time.LocalDateTime

@DynamoDBTable(tableName = "custom_attribute")
data class CustomAttribute (

        @Id 
        @DynamoDBHashKey(attributeName = "custom_attribute_id")
        var id: String?,

        @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.S)
        @get:DynamoDBAttribute(attributeName = "property_name")
        var propertyName: String?,

        @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.S)
        @get:DynamoDBAttribute(attributeName = "value")
        var value: String?,

        @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.S)
        @get:DynamoDBAttribute(attributeName = "account_id")
        var accountId: String?,

        @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.S)
        @get:DynamoDBAttribute(attributeName = "creation_date")
        @DynamoDBTypeConverted(converter = LocalDateTimeConverter::class)
        var creationDate: LocalDateTime?
){
        constructor() : this(null, null, null, null, null)
}