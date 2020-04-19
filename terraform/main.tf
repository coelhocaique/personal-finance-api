data "aws_caller_identity" "current" {}
data "aws_region" "current" {}
locals {
  account_id = "${data.aws_caller_identity.current.account_id}"
}
resource "aws_dynamodb_table" "personal-finance-income" {
  name = "income"
  read_capacity = "1"
  write_capacity = "1"
  hash_key = "income_id"

  attribute {
    name = "income_id"
    type = "S"
  }
}
resource "aws_dynamodb_table" "personal-finance-debt" {
  name = "debt"
  read_capacity = "1"
  write_capacity = "1"
  hash_key = "debt_id"

  attribute {
    name = "debt_id"
    type = "S"
  }
}
resource "aws_dynamodb_table" "personal-finance-parameter" {
  name = "parameter"
  read_capacity = "1"
  write_capacity = "1"
  hash_key = "parameter_id"

  attribute {
    name = "parameter_id"
    type = "S"
  }
}
resource "aws_dynamodb_table" "personal-finance-custom-attribute" {
  name = "custom_attribute"
  read_capacity = "1"
  write_capacity = "1"
  hash_key = "custom_attribute_id"

  attribute {
    name = "custom_attribute_id"
    type = "S"
  }
}
resource "aws_dynamodb_table" "personal-finance-recurring-debt" {
  name = "recurring_debt"
  read_capacity = "1"
  write_capacity = "1"
  hash_key = "recurring_debt_id"

  attribute {
    name = "recurring_debt_id"
    type = "S"
  }
}