package as.cron.cli;

import as.cron.cli.parser.CronExpressionParser;
import as.cron.cli.parser.ValidationException;

public class Main {

    private static final CronExpressionParser CRON_EXPRESSION_PARSER = new CronExpressionParser();

    public static void main(String[] args) {
        final String input = args[0];

        try {
            System.out.println(CRON_EXPRESSION_PARSER.parse(input));
        } catch (ValidationException e){
            System.out.println("Validation failed: " + e.getMessage());
        } catch (Exception e){
            System.out.println("Unexpected exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}