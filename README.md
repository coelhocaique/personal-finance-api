# Personal Finance API
[![Build Status](https://travis-ci.com/coelhocaique/personal-finance-api.svg?branch=master)](https://travis-ci.com/coelhocaique/personal-finance-api)
<!-- [![Code Coverage](https://codecov.io/github/coelhocaique/personal-finance-api.svg)](https://codecov.io/gh/coelhocaique/personal-finance-api)-->

## Technologies

* [Koltin](https://kotlinlang.org/)
* [Spring Web Flux](https://docs.spring.io/spring-framework/docs/5.0.0.BUILD-SNAPSHOT/spring-framework-reference/html/web-reactive.html)
* [DynamoDB](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Introduction.html)

## Introduction
Control your personal money with Personal Finance API, it is a simple, cool and secure way to store the personal information about your money. This REST API allows you to create, access and manage your data and it do not store any personal sensitive information such as name, email and etc.

## Create DynamoDB tables

Before running the project for the first time, the databases on AWS must be created. To do so, [terraform](https://www.terraform.io/downloads.html) CLI is required.
Also, AWS credentials (**AWS_ACCESS_KEY_ID** and **AWS_SECRET_ACCESS_KEY**) are required as environment variables in your local machine.
After that, execute the terraform scripts:
```
cd terraform
terraform apply
```

## Run 

Run locally with gradle:

***Make sure to have your AWS credentials as environment variables in your local machine.***

```
./gradlew bootRun

```
Or run with [docker](https://docs.docker.com/get-docker/):

***Add your AWS credentials to [this file](https://github.com/coelhocaique/personal-finance-api/blob/master/docker/pf-environment.list)***
```
cd docker
docker-compose up
```

## Checkout how to use the API

[Swagger Documentation](https://app.swaggerhub.com/apis-docs/coelhocaique/personal-finance_api/1.0.0#/)

[Docker hub repository](https://hub.docker.com/repository/docker/coelhocaique/personal-finance-api)


