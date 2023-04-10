package as.cron.cli.parser;

public class CronExpressionParser {

    private static final int LABEL_COLUMNS_WIDTH = 14;

    private static final FieldExpressionParser MINUTE_PARSER = new NumberExpressionParser("minute", 0, 59);
    private static final FieldExpressionParser HOUR_PARSER = new NumberExpressionParser("hour", 0, 23);
    private static final FieldExpressionParser DAY_OF_MONTH_PARSER = new DayOfMonthExpressionParser();
    private static final FieldExpressionParser MONTH_PARSER = new MonthExpressionParser();
    private static final FieldExpressionParser DAY_OF_WEEK_PARSER = new DayOfWeekExpressionParser();

    public String parse(String cronExpression) {
        final String[] split = cronExpression.split(" ", 6);

        if (split.length != 6) {
            throw new ValidationException(String.format(
                    "Invalid input params %s. There should be 5 fields in cron expression + 1 extra command."
                            + "\n Example: */15 0 1,15 * 1-5 /usr/bin/find", cronExpression));
        }

        final String minute = MINUTE_PARSER.parse(split[0]);
        final String hour = HOUR_PARSER.parse(split[1]);
        final String dayOfMonth = DAY_OF_MONTH_PARSER.parse(split[2]);
        final String month = MONTH_PARSER.parse(split[3]);
        final String dayOfWeek = DAY_OF_WEEK_PARSER.parse(split[4]);

        return label(MINUTE_PARSER.getFieldName())
                + minute
                + "\n"
                + label(HOUR_PARSER.getFieldName())
                + hour
                + "\n"
                + label(DAY_OF_MONTH_PARSER.getFieldName())
                + dayOfMonth
                + "\n"
                + label(MONTH_PARSER.getFieldName())
                + month
                + "\n"
                + label(DAY_OF_WEEK_PARSER.getFieldName())
                + dayOfWeek
                + "\n"
                + label("command")
                + split[5];
    }

    private String label(String input) {
        return String.format("%1$-" + LABEL_COLUMNS_WIDTH + "s", input);
    }
}
