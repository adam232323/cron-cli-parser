package as.cron.cli.parser;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class NumberExpressionParser extends AbstractFieldExpressionParser {

    private final int min;
    private final int max;
    private final boolean questionMarkAllowed;

    NumberExpressionParser(final String fieldName, final int min, final int max) {
        this(fieldName, min, max, false);
    }

    NumberExpressionParser(final String fieldName,
                           final int min,
                           final int max,
                           final boolean questionMarkAllowed) {
        super(fieldName);
        this.min = min;
        this.max = max;
        this.questionMarkAllowed = questionMarkAllowed;
    }

    @Override
    protected String allowedCharsRegexp() {
        return questionMarkAllowed ? "[0-9,\\-\\?\\*/]+" : "[0-9,\\-\\*/]+";
    }

    @Override
    protected String doParse(final String field) {
        return Arrays.stream(field.split(","))
                .flatMap(this::parseCommaSplit)
                .sorted()
                .map(String::valueOf)
                .collect(Collectors.joining(" "));
    }

    private Stream<Integer> parseCommaSplit(String field) {
        return field.contains("/") ? parseInterval(field) : parseRange(field);
    }

    private Stream<Integer> parseInterval(String field) {
        String[] split = field.split("/");

        if (split.length > 2) {
            throwValidationException("Interval has to many values", field);
        }

        Range range = parseToRange(split[0]);
        int interval = Integer.parseInt(split[1]);

        if (interval < 1) {
            throwValidationException("Interval mast be higher then 1", field);
        }

        List<Integer> intervalValues = new LinkedList<>();
        final int end = split[0].contains("-") ? range.end : max;
        for (int i = range.start; i <= end; i += interval) {
            intervalValues.add(i);
        }
        return intervalValues.stream();
    }

    private Stream<Integer> parseRange(String field) {
        final Range range = parseToRange(field);
        validateRange(field, range);

        return IntStream.rangeClosed(range.start, range.end).boxed();
    }

    private Range parseToRange(String field) {
        if (field.contains("*") || questionMarkAllowed && field.contains("?")) {
            return new Range(min, max);
        }
        if (field.contains("-")) {
            String[] split = field.split("-");
            if (split.length > 2) {
                throwValidationException("Range has to many values", field);
            }
            return new Range(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
        } else {
            final int value = Integer.parseInt(field);
            return new Range(value, value);
        }
    }

    private void validateRange(String field, Range range) {
        final int start = range.start;
        final int end = range.end;
        if (start > max || end > max) {
            throwValidationException("Range too big", field);
        }
        if (start < min || end < min) {
            throwValidationException("Range too low", field);
        }
        if (start > end) {
            throwValidationException("Range is inverted", field);
        }
    }

    private static class Range {
        private final int start;
        private final int end;

        private Range(final int start, final int end) {
            this.start = start;
            this.end = end;
        }
    }
}
