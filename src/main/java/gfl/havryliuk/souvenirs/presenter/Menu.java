package gfl.havryliuk.souvenirs.presenter;

import gfl.havryliuk.souvenirs.entities.Entity;
import gfl.havryliuk.souvenirs.util.ConsoleReader;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Menu {

    @SneakyThrows
    public static int showItemsAndGetAnswer(List<String> items) {
        int userChoice = -1;
        do {
            log.debug("Choose the option: ");
            buildOptions(items);
            buildExitOption(items);
            String userInput = ConsoleReader.readLine();
            if (warnIfNotNumber(userInput)) continue;
            userChoice = Integer.parseInt(userInput);
            warnIfOutOfRange(items, userChoice);
        } while (userChoice < 0 || userChoice >= items.size());
        return userChoice;
    }

    @SneakyThrows
    public static int showEntitiesAndGetAnswer(List<? extends Entity> entities, String message) {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < entities.size(); i++) {
            items.add(i, entities.get(i).toString());
        }
        log.debug("{}", message);
        return showItemsAndGetAnswer(items);
    }


    public static void showEntities(List<? extends Entity> entities) {
        log.debug("{}", entities);
//        List<String> items = new ArrayList<>();
//        for (int i = 0; i < entities.size(); i++) {
//            items.add(i, entities.get(i).toString());
//        }
//        buildOptions(items);
    }


    private static void buildOptions(List<String> names) {
        for (int i = 1; i < names.size(); i++) {
            log.debug("{} {}", i, names.get(i));
        }
    }

    private static void buildExitOption(List<String> names) {
        if(!names.isEmpty()) {
            log.debug("{} {}", 0, names.get(0));
        }
    }

    private static boolean warnIfNotNumber(String userInput) {
        if (!StringUtils.isNumeric(userInput)) {
            log.warn("'{}' isn't numeric. Choose the correct option please.", userInput);
            return true;
        }
        return false;
    }

    private static void warnIfOutOfRange(List<String> names, int userChoice) {
        if (userChoice < 0 || userChoice >= names.size()) {
            log.warn("'{}' is not a point of menu. Choose the correct option please.", userChoice);
        }
    }


}
