package gfl.havryliuk.souvenirs.util.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    public static boolean isNotValid(String toCheck, String pattern) {
        Pattern regexPattern = Pattern.compile(pattern);
        Matcher regexMatcher = regexPattern.matcher(toCheck);
        return !regexMatcher.matches();
    }
}
