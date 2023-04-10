package as.cron.cli.parser;

interface FieldExpressionParser {
    String parse(String field);

    String getFieldName();
}
