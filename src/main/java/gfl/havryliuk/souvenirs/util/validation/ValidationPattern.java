package gfl.havryliuk.souvenirs.util.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@SuppressWarnings("ALL")
@Getter
@AllArgsConstructor
public enum ValidationPattern {

    NAME("^[\\\"'\\-\\s№,.()A-Za-zА-ЯІЇЄҐа-яіїґє]{2,50}$",
            " should be from 2 to 50 letters long, contains letter, signs ', \\\", -,(), № or space."),
    COUNTRY("^['\\-\\sA-Za-zА-ЯІЇЄҐа-яіїґє]{2,50}$",
            " should be from 2 to 24 letters long, contains only letter, signs ' and - or space."),
    PRICE("^(\\d+(\\.\\d+)?)$", " should contains only digits and point."),
    PRODUCTION_YEAR("^((18)|(19)|(20))\\d\\d$", " should contains only four digits. " +
            "(The latest date could be 1800)"),
    DATE("^((18)|(19)|(20))\\d\\d-((0[1-9])|(1[012]))-((0[1-9])|([12][0-9])|(3[01]))$",
            " is incorrect. Check pattern and try again.");

    private final String pattern;
    private final String errorMessage;

}
