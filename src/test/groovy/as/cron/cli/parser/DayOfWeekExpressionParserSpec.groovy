package as.cron.cli.parser

import spock.lang.Specification

class DayOfWeekExpressionParserSpec extends Specification {

    def parser = new DayOfWeekExpressionParser()

    def "should parse day of month"() {
        expect:
        expected == parser.parse(input)

        where:
        input                         || expected
        "*"                           || "0 1 2 3 4 5 6 7"
        "MON,TUE,WED,THU,FRI,SAT,SUN" || "1 2 3 4 5 6 7"
        "0-6"                         || "0 1 2 3 4 5 6"
        "MON-SUN"                     || "1 2 3 4 5 6 7"
        "TUE,WED"                     || "2 3"
        "tue,wed"                     || "2 3"
        "TUE/2"                       || "2 4 6"
        "0L"                          || "last sunday of the month"
        "1L"                          || "last monday of the month"
        "SUNL"                        || "last sunday of the month"
        "FRI#1"                       || "1st friday of month"
        "FRI#2"                       || "2nd friday of month"
        "4#3"                         || "3rd thursday of month"
        "2#4"                         || "4th tuesday of month"
    }
}
