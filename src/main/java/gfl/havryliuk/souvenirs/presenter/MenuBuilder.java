package gfl.havryliuk.souvenirs.presenter;

import gfl.havryliuk.souvenirs.util.ConsoleReader;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class MenuBuilder {

    @SneakyThrows
    public static int build(String[] names) {
        int userChoice = -1;
        do {
            log.debug("Choose the option: ");
            buildOptions(names);
            buildExitOption(names);
            String userInput = ConsoleReader.readLine();
            if (warnIfNotNumber(userInput)) continue;
            userChoice = Integer.parseInt(userInput);
            warnIfOutOfRange(names, userChoice);
        } while (userChoice < 0 || userChoice >= names.length);
        return userChoice;
    }

    private static void buildOptions(String[] names) {
        for (int i = 1; i < names.length; i++) {
            log.debug("{} {}", i, names[i]);
        }
    }

    private static void buildExitOption(String[] names) {
        if(names.length != 0) {
            log.debug("{} {}", 0, names[0]);
        }
    }

    private static boolean warnIfNotNumber(String userInput) {
        if (!StringUtils.isNumeric(userInput)) {
            log.warn("'{}' is not a number. Choose the correct option please.", userInput);
            return true;
        }
        return false;
    }

    private static void warnIfOutOfRange(String[] names, int userChoice) {
        if (userChoice < 0 || userChoice >= names.length) {
            log.warn("'{}' is not a point of menu. Choose the correct option please.", userChoice);
        }
    }


}
