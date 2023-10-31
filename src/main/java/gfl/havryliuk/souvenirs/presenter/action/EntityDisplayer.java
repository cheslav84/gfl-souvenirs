package gfl.havryliuk.souvenirs.presenter.action;

import gfl.havryliuk.souvenirs.entities.Entity;
import gfl.havryliuk.souvenirs.presenter.Menu;

import gfl.havryliuk.souvenirs.presenter.printer.ConsoleLoggingPrinter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

    @Slf4j
    @Setter
    public abstract class EntityDisplayer<T extends Entity> implements Action, ReturnableAction<Entity> {//todo rename

    protected List<? extends Entity> entities;

    protected ConsoleLoggingPrinter<? extends Entity> printer;

    protected abstract List<? extends Entity> setEntities();
    protected abstract ConsoleLoggingPrinter<? extends Entity> setPrinter();

    @Override
    public void execute() {//todo порушує SingleResponsibility?. Подумати, можливо переробити якщо буде час.
        entities = setEntities();
        printer = setPrinter();
        printer.print();
        log.debug("Main menu.");
    }

    @Override
    public Optional<Entity> executeAndReturn() {//todo порушує SingleResponsibility?. Подумати, можливо переробити якщо буде час.
        entities = setEntities();
        if(!entities.isEmpty()) {
            int userChoice = Menu.showEntitiesAndGetAnswer(entities,
                    "To choose the producer press the number against it.");
            return Optional.of(entities.get(userChoice));
        } else {
            return Optional.empty();
        }
    }

}
