package as.cron.cli.parser;

import java.util.List;

class DayOfWeekExpressionParser extends AbstractFieldExpressionParser {

    private static final String FIELD_NAME = "day of week";

    private final List<String> DAY_SHORT_NAMES = List.of("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN");
    private final List<String> DAY_FULL_NAMES = List.of("monday",
                                                        "tuesday",
                                                        "wednesday",
                                                        "thursday",
                                                        "friday",
                                                        "saturday",
                                                        "sunday");

    private final NumberExpressionParser numberExpressionParser = new NumberExpressionParser(FIELD_NAME, 0, 7);

    DayOfWeekExpressionParser() {
        super(FIELD_NAME);
    }

    @Override
    protected String allowedCharsRegexp() {
        return "(?i)[0-9,\\-\\?\\*/L#(MON)(TUE)(WED)(THU)(FIR)(SAT)(SUN)]+";
    }

    @Override
    protected String doParse(final String field) {
        String replaced = field.toUpperCase();
        for (int i = 1; i <= 7; i++) {
            replaced = replaced.replace(DAY_SHORT_NAMES.get(i - 1), String.valueOf(i));
        }

        if (replaced.contains("L")) {
            return parseLastDay(replaced);
        }

        if (replaced.contains("#")) {
            return parseNthDay(replaced);
        }

        return numberExpressionParser.parse(replaced);
    }

    private String parseLastDay(final String field) {
        int dayNum = Integer.parseInt(field.replace("L", ""));
        String dayName = getFullDayName(dayNum);

        return String.format("last %s of the month", dayName);
    }

    private String parseNthDay(final String field) {
        final String[] split = field.split("#");
        final String dayName = getFullDayName(Integer.parseInt(split[0]));

        return String.format("%s %s of month", getNthName(Integer.parseInt(split[1])), dayName);
    }

    private String getFullDayName(final int dayNum) {
        return DAY_FULL_NAMES.get(dayNum == 0 ? 6 : dayNum - 1);
    }

    private String getNthName(int dayNum) {
        switch (dayNum) {
            case 1:
                return "1st";
            case 2:
                return "2nd";
            case 3:
                return "3rd";
            default:
                return dayNum + "th";
        }
    }
}
