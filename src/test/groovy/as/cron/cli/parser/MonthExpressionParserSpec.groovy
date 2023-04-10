package as.cron.cli.parser

import spock.lang.Specification

class MonthExpressionParserSpec extends Specification {

    def parser = new MonthExpressionParser()

    def "should parse day of month"() {
        expect:
        expected == parser.parse(input)

        where:
        input                                             || expected
        "*"                                               || "1 2 3 4 5 6 7 8 9 10 11 12"
        "MAR/2"                                           || "3 5 7 9 11"
        "JAN-DEC"                                         || "1 2 3 4 5 6 7 8 9 10 11 12"
        "JAN,FEB,MAR,APR,MAY,JUN,JUL,AUG,SEP,OCT,NOV,DEC" || "1 2 3 4 5 6 7 8 9 10 11 12"
        "MAR,MAY"                                         || "3 5"
        "mar,may"                                         || "3 5"
    }
}
