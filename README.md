# cron-cli-parser

This solution is based on Spring cron expression.
https://productresources.collibra.com/docs/collibra/latest/Content/Cron/co_spring-cron.htm

## Requirements

Java11, Maven

## Build 

To build application use Maven
`mvn clean package`

To execute program use cmd

`java -jar target/cli-cron-parser-1.0.0.jar <cron-expression>`

example invocation
`java -jar target/cli-cron-parser-1.0.0.jar "*/15 0 1,15 * 1-5 /usr/bin/find"`



