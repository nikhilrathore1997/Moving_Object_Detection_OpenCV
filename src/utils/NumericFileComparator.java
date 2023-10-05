package utils;

import java.io.File;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumericFileComparator implements Comparator<File> {
    private static final Pattern PATTERN = Pattern.compile("\\d+");

    @Override
    public int compare(File file1, File file2) {
        Matcher matcher1 = PATTERN.matcher(file1.getName());
        Matcher matcher2 = PATTERN.matcher(file2.getName());

        while (matcher1.find() && matcher2.find()) {
            int number1 = Integer.parseInt(matcher1.group());
            int number2 = Integer.parseInt(matcher2.group());

            if (number1 != number2) {
                return Integer.compare(number1, number2);
            }
        }

        // If one file has more numbers, it should come first
        return Integer.compare(matcher1.groupCount(), matcher2.groupCount());
    }
}