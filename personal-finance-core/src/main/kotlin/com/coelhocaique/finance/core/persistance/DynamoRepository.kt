package com.coelhocaique.finance.core.persistance

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.amazonaws.services.dynamodbv2.model.DeleteItemRequest
import com.amazonaws.services.dynamodbv2.model.GetItemRequest
import com.amazonaws.services.dynamodbv2.model.ScanRequest
import com.coelhocaique.finance.core.util.logger
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component

@Suppress("UNCHECKED_CAST")
@Component
class DynamoRepository(val db: AmazonDynamoDB,
                       val objectMapper: ObjectMapper) {

    fun <T> getItem(tableName: String, key: Map<String, String>, clazz: Class<T>): T? {
        val request = GetItemRequest()
                .withTableName(tableName)
                .withKey(convertToDb(key))
        return convertToObject(db.getItem(request).item, clazz)
    }

    fun <T> scanItemsBetween(tableName: String,
                             fieldBetween: String,
                             betweenKey: Map<String, String>,
                             otherKeys: Map<String, String>,
                             clazz: Class<T>): List<T>? {
        val filterExpression =
                buildFilterExpression(otherKeys)
                .plus(" AND ")
                .plus(fieldBetween).plus(" BETWEEN ")
                .plus(buildFilterExpression(betweenKey, ""))

        logger().info("filterExpression=".plus(filterExpression))
        return scan(tableName, betweenKey + otherKeys, filterExpression, clazz)
    }

    fun <T> scanItems(tableName: String, key: Map<String, String>, clazz: Class<T>): List<T>? {
        return scan(tableName, key, buildFilterExpression(key), clazz)
    }

    fun <T> scanAll(tableName: String, clazz: Class<T>): List<T> {
        val request = ScanRequest(tableName)
        return convertToMap(db.scan(request).items, clazz)
    }

    fun <T> addItem(tableName: String, o: T){
        db.putItem(tableName, convertToDb(writeKeys(o)))
    }

    fun deleteItem(tableName: String, key: Map<String, String>){
        val request = DeleteItemRequest().withTableName(tableName).withKey(convertToDb(key))
        db.deleteItem(request)
    }

    private fun <T> scan(tableName: String, key: Map<String, String>, filterExpression: String, clazz: Class<T>): List<T>? {
        val request = ScanRequest(tableName)
                .withFilterExpression(filterExpression)
                .withExpressionAttributeValues(buildExpressionAttributeValues(key))
        val result = convertToMap(db.scan(request).items, clazz)

        return if (result.isNotEmpty()) result else null
    }

    private fun convertToDb(map: Map<String, Any>): Map<String, AttributeValue> {
        return map.map {
            when(it.value){
                is Collection<*> -> {
                    val nested = it.value as List<Map<String, Any>>
                    it.key to AttributeValue(toJson(nested))
                }
                else -> it.key to AttributeValue(it.value.toString())
            }
        }.toMap()
    }

    private fun <T> convertToMap(attrs: List<Map<String, AttributeValue>>, o: Class<T>): List<T> {
        return attrs.map { readKeys(convertToMap(it), o) }
    }

    private fun <T> convertToObject(attrs: Map<String, AttributeValue>?, o: Class<T>): T? {
        return if (attrs != null) readKeys(convertToMap(attrs), o) else null
    }

    private fun convertToMap(map: Map<String, AttributeValue>): Map<String, Any> {
        return map.map {
            if ("[" in it.value.s)
                it.key to fromJson(it.value.s, List::class.java)
            else
                it.key to it.value.s
        }.toMap()
    }

    private fun buildFilterExpression(filter: Map<String, String>, expression: String = "="): String {
        return if (expression.isNotEmpty())
            filter.keys.joinToString(" AND ") { it.plus(expression).plus(":").plus(it) }
        else
            filter.keys.joinToString(" AND ") { ":".plus(it) }
    }

    private fun buildExpressionAttributeValues(filter: Map<String, String>): Map<String, AttributeValue> {
        return filter.map { ":".plus(it.key) to AttributeValue(it.value) }.toMap()
    }

    private fun <T> writeKeys(o: T) =
            objectMapper.readValue(toJson(o), Map::class.java) as Map<String, Any>

    private fun <T> readKeys(json: Map<String, Any>, o: Class<T>) =
            objectMapper.readValue(toJson(json), o)

    private fun <T> toJson(o: T) = objectMapper.writeValueAsString(o)

    private fun <T> fromJson(o: String, c: Class<T>) = objectMapper.readValue(o, c)

}