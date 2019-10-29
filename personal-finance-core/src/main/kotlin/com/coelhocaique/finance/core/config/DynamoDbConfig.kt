package com.coelhocaique.finance.core.config

import com.amazonaws.ClientConfiguration
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class DynamoDbConfig(@Value("\${dynamo.hostname}") val hostname: String,
                     @Value("\${dynamo.region}") val region: String) {

    @Bean
    @Primary
    fun dynamoDBMapper(amazonDynamoDB: AmazonDynamoDB): DynamoDBMapper = DynamoDBMapper(amazonDynamoDB, dynamoDBMapperConfig())

    @Bean
    fun amazonDynamoDB(): AmazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
            .withEndpointConfiguration(endpointDBConfiguration())
            .withClientConfiguration(ClientConfiguration()
                    .withClientExecutionTimeout(5_000)
                    .withSocketTimeout(5_000)
                    .withConnectionTimeout(5_000)
                    .withRequestTimeout(5_000))
            .build()

    private fun dynamoDBMapperConfig(): DynamoDBMapperConfig = DynamoDBMapperConfig.builder()
            .withPaginationLoadingStrategy(DynamoDBMapperConfig.PaginationLoadingStrategy.LAZY_LOADING)
            .build()

    private fun endpointDBConfiguration(): AwsClientBuilder.EndpointConfiguration = AwsClientBuilder.EndpointConfiguration(hostname, region)
}