package as.cron.cli.parser

import spock.lang.Specification

class NumberExpressionParserSpec extends Specification {

    def "should parse range field expression"() {
        expect:
        expected == new NumberExpressionParser("test", 0, 5, true).parse(input as String)

        where:
        input || expected
        "*"   || "0 1 2 3 4 5"
        "?"   || "0 1 2 3 4 5"
        "2"   || "2"
        "2-4" || "2 3 4"
    }

    def "should parse interval field expression"() {
        expect:
        expected == new NumberExpressionParser("test", 0, 59).parse(input as String)

        where:
        input          || expected
        "*/10"         || "0 10 20 30 40 50"
        "20/10"        || "20 30 40 50"
        "5-45/10"      || "5 15 25 35 45"
        "8-30/10"      || "8 18 28"
        "8-30/10,*/15" || "0 8 15 18 28 30 45"
    }

    def "should parse comma separated field expression"() {
        expect:
        expected == new NumberExpressionParser("test", 0, 10).parse(input as String)

        where:
        input   || expected
        "2,4,5" || "2 4 5"
    }

    def "should fail parsing expression"() {
        expect:
        def message = null
        try {
            new NumberExpressionParser("test", 2, 4).parse(input as String)
        } catch (ValidationException e) {
            message = e.getMessage()
        }

        expected == message

        where:
        input     || expected
        "1-2-3"   || "[test] Range has to many values: 1-2-3"
        "2-10"    || "[test] Range too big: 2-10"
        "1-4"     || "[test] Range too low: 1-4"
        "4-2"     || "[test] Range is inverted: 4-2"
        "4/10/10" || "[test] Interval has to many values: 4/10/10"
        "4/0"     || "[test] Interval mast be higher then 1: 4/0"
        "ad2ac"   || "[test] Invalid characters found: ad2ac"
    }
}
