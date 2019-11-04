package com.coelhocaique.finance.core.domain

import com.amazonaws.services.dynamodbv2.datamodeling.*
import com.coelhocaique.finance.core.domain.converters.LocalDateConverter
import com.coelhocaique.finance.core.domain.converters.LocalDateTimeConverter
import org.springframework.data.annotation.Id
import java.time.LocalDate
import java.time.LocalDateTime

@DynamoDBTable(tableName = "debt")
data class Debt (

        @Id 
        @DynamoDBHashKey(attributeName = "debt_id")
        var id: String?,

        @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.S)
        @get:DynamoDBAttribute(attributeName = "amount")
        var amount: String?,

        @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.S)
        @get:DynamoDBAttribute(attributeName = "description")
        var description: String?,

        @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.S)
        @get:DynamoDBAttribute(attributeName = "debt_date")
        @DynamoDBTypeConverted(converter = LocalDateConverter::class)
        var debtDate: LocalDate?,

        @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.S)
        @get:DynamoDBAttribute(attributeName = "reference_code")
        var referenceCode: String?,

        @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.S)
        @get:DynamoDBAttribute(attributeName = "installment_number")
        var installmentNumber: String?,

        @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.S)
        @get:DynamoDBAttribute(attributeName = "reference_date")
        var referenceDate: String?,

        @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.S)
        @get:DynamoDBAttribute(attributeName = "type")
        var type: String?,

        @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.S)
        @get:DynamoDBAttribute(attributeName = "tag")
        var tag: String?,

        @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.S)
        @get:DynamoDBAttribute(attributeName = "installments")
        var installments: String?,

        @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.S)
        @get:DynamoDBAttribute(attributeName = "total_amount")
        var totalAmount: String?,

        @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.S)
        @get:DynamoDBAttribute(attributeName = "user_id")
        var userId: String?,

        @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.S)
        @get:DynamoDBAttribute(attributeName = "creation_date")
        @DynamoDBTypeConverted(converter = LocalDateTimeConverter::class)
        var creationDate: LocalDateTime?
){
        constructor() : this(null, null, null, null, null, null, null, null, null, null, null, null, null)
}