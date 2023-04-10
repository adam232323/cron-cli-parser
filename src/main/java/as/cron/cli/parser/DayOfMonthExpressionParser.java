package as.cron.cli.parser;

class DayOfMonthExpressionParser extends AbstractFieldExpressionParser {

    private static final String FIELD_NAME = "day of month";

    private final NumberExpressionParser numberExpressionParser = new NumberExpressionParser("day of month",
                                                                                             1,
                                                                                             31,
                                                                                             true);

    DayOfMonthExpressionParser() {
        super(FIELD_NAME);
    }

    @Override
    protected String allowedCharsRegexp() {
        return "[0-9,\\-\\?\\*/LW]+";
    }

    @Override
    protected String doParse(final String field) {
        if (field.equals("LW")) {
            return "last weekday of the month";
        }
        if (field.contains("L")) {
            return parseLastDayOfMonth(field);
        }
        if (field.contains("W")) {
            return parseNearestWorkday(field);
        }
        return numberExpressionParser.parse(field);
    }

    private String parseNearestWorkday(final String field) {
        final int dayOfMonth = Integer.parseInt(field.replace("W", ""));

        return String.format("nearest workday of %s", dayOfMonth);
    }

    private String parseLastDayOfMonth(final String field) {
        if (field.equals("L")) {
            return "last day of month";
        }

        final String[] split = field.split("-");

        if (split.length > 2) {
            throwValidationException("Invalid last day of month expression", field);
        }

        final int offset = Integer.parseInt(split[1]);
        return String.format("%d days before last day of month", offset);
    }
}
