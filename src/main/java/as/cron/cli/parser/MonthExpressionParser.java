package as.cron.cli.parser;

import java.util.List;

class MonthExpressionParser extends AbstractFieldExpressionParser {

    private static final String FIELD_NAME = "month";

    private final List<String> MONTH_NAMES = List.of("JAN",
                                                     "FEB",
                                                     "MAR",
                                                     "APR",
                                                     "MAY",
                                                     "JUN",
                                                     "JUL",
                                                     "AUG",
                                                     "SEP",
                                                     "OCT",
                                                     "NOV",
                                                     "DEC");

    private final NumberExpressionParser numberExpressionParser = new NumberExpressionParser(FIELD_NAME, 1, 12);

    MonthExpressionParser() {
        super(FIELD_NAME);
    }

    @Override
    protected String allowedCharsRegexp() {
        return "(?i)[0-9,\\-\\?\\*/(JAN)(FEB)(MAR)(APR)(MAY)(JUN)(JUL)(AUG)(SEP)(OCT(NOV)(DEC))]+";
    }

    @Override
    protected String doParse(final String field) {
        String replaced = field.toUpperCase();
        for (int i = 1; i <= 12; i++) {
            replaced = replaced.replace(MONTH_NAMES.get(i - 1), String.valueOf(i));
        }
        return numberExpressionParser.parse(replaced);
    }
}
