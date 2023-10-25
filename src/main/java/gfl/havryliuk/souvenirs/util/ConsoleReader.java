package gfl.havryliuk.souvenirs.util;

import gfl.havryliuk.souvenirs.util.validation.ValidationPattern;
import gfl.havryliuk.souvenirs.util.validation.Validator;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

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
        String userInput = readLine();
        while (Validator.isNotValid(userInput, pattern.getPattern())) {
            log.warn(capitalizeFirstLetter(valueToRead) + pattern.getErrorMessage());
            userInput = readLine();
        }
        return capitalizeFirstLetter(userInput);
    }

    private static String capitalizeFirstLetter(String valueToRead) {
        return valueToRead.substring(0, 1).toUpperCase() + valueToRead.substring(1);
    }


}
