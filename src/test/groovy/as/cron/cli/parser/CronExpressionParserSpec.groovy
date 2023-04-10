package as.cron.cli.parser

import spock.lang.Specification

class CronExpressionParserSpec extends Specification {

    public static final String EXPECTED_RESULT = "minute        0 15 30 45\n" +
            "hour          0\n" +
            "day of month  1 15\n" +
            "month         1 2 3 4 5 6 7 8 9 10 11 12\n" +
            "day of week   1 2 3 4 5\n" +
            "command       /usr/bin/find"

    def parser = new CronExpressionParser()

    def "should parse cron expression"() {
        given:
        def expression = "*/15 0 1,15 * 1-5 /usr/bin/find"

        when:
        def result = parser.parse(expression)

        println result

        then:
        result == EXPECTED_RESULT
    }
}
