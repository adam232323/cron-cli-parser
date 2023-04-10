package as.cron.cli.parser

import spock.lang.Specification

class DayOfMonthExpressionParserSpec extends Specification {

    def parser = new DayOfMonthExpressionParser()

    def "should parse last day of Month"() {
        expect:
        expected == parser.parse(input)

        where:
        input || expected
        "L"   || "last day of month"
        "L-4" || "4 days before last day of month"
        "LW"  || "last weekday of the month"
    }

    def "should parse nearest workday in Month"() {
        expect:
        expected == parser.parse(input)

        where:
        input || expected
        "1W"  || "nearest workday of 1"
        "14W" || "nearest workday of 14"
    }

    def "should fail parsing expression"() {
        expect:
        def message = null
        try {
            parser.parse(input as String)
        } catch (ValidationException e) {
            message = e.getMessage()
        }

        expected == message

        where:
        input    || expected
        "L-23-2" || "[day of month] Invalid last day of month expression: L-23-2"
    }
}
