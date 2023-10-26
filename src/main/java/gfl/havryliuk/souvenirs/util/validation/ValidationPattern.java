package gfl.havryliuk.souvenirs.util.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ValidationPattern {

    NAME("^[\\\"'\\-\\s№,.()A-Za-zА-ЯІЇЄҐа-яіїґє]{2,50}$",
            " should be from 2 to 50 letters long, contains letter, signs ', \\\", -,(), № or space."),
    COUNTRY("^['\\-\\sA-Za-zА-ЯІЇЄҐа-яіїґє]{2,50}$",
            " should be from 2 to 24 letters long, contains only letter, signs ' and - or space."),
    DATE("^((19)|(20))\\d\\d-((0[1-9])|(1[012]))-((0[1-9])|([12][0-9])|(3[01]))$", "");
//    DATE("^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d$", "");

    private final String pattern;
    private final String errorMessage;


//    ValidationPatterns(String pattern, String errorMessage) {
//        this.pattern = pattern;
//        this.errorMessage = errorMessage;
//    }
}
