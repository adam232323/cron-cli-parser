package as.cron.cli.parser;

abstract class AbstractFieldExpressionParser implements FieldExpressionParser {

    private final String fieldName;

    AbstractFieldExpressionParser(final String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String parse(final String field) {
        try {
            validateAllowedChars(field);
            return doParse(field);
        } catch (NumberFormatException e) {
            throwValidationException("Cannot parse field", field);
            e.printStackTrace();
            return "";
        }
    }

    private void validateAllowedChars(String field) {
        if (!field.matches(allowedCharsRegexp())) {
            throwValidationException("Invalid characters found", field);
        }
    }

    protected abstract String allowedCharsRegexp();

    protected abstract String doParse(final String field);

    protected void throwValidationException(final String message, final String field) {
        throw new ValidationException(String.format("[%s] %s: %s", fieldName, message, field));
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }
}
