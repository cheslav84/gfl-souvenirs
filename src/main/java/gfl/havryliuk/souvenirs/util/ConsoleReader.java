package gfl.havryliuk.souvenirs.util;

import gfl.havryliuk.souvenirs.util.validation.ValidationPattern;
import gfl.havryliuk.souvenirs.util.validation.Validator;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Slf4j
public final class ConsoleReader {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    @SneakyThrows
    public static String readLine() {
        return READER.readLine().trim();
    }

    @SneakyThrows
    public static String readString(String valueToRead, ValidationPattern pattern) {
        log.debug("Enter {}:", valueToRead);
        String userInput = readLine().trim();
        while (Validator.isNotValid(userInput, pattern.getPattern())) {
            log.warn(capitalizeFirstLetter(valueToRead) + pattern.getErrorMessage());
            userInput = readLine().trim();
        }
        return capitalizeFirstLetter(userInput);
    }

    @SneakyThrows
    public static String readForUpdatingString(String existedValue, String valueToRead, ValidationPattern pattern) {
        log.debug("Enter {}, or press 0 to remain '{}' the same:", valueToRead, existedValue);
        String userInput = readLine().trim();

        while (!userInput.equals("0") && Validator.isNotValid(userInput, pattern.getPattern())) {
            log.warn(capitalizeFirstLetter(valueToRead) + pattern.getErrorMessage());
            userInput = readLine().trim();
        }
        if (userInput.equals("0")) {
            return existedValue;
        }
        return capitalizeFirstLetter(userInput);
    }



    @SneakyThrows
    public static double readDouble(String valueToRead, ValidationPattern pattern) {
        log.debug("Enter {}:", valueToRead);
        String userInput = readLine().replace(",", ".").trim();
        while (Validator.isNotValid(userInput, pattern.getPattern())) {
            log.warn(capitalizeFirstLetter(valueToRead) + pattern.getErrorMessage());
            log.debug("Enter {}:", valueToRead);
            userInput = readLine().replace(",", ".").trim();
        }
        return Double.parseDouble(userInput);
    }

    private static String capitalizeFirstLetter(String valueToRead) {
        return valueToRead.substring(0, 1).toUpperCase() + valueToRead.substring(1);
    }

}
