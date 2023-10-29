package gfl.havryliuk.souvenirs.presenter.action;

import gfl.havryliuk.souvenirs.entities.Entity;
import gfl.havryliuk.souvenirs.presenter.Menu;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

    @Slf4j
    @Setter
    public abstract class EntityDisplayer implements Action, ReturnableAction<Entity> {//todo rename

    protected List<? extends Entity> entities;

    @Override
    public void execute() {
        entities = setEntities();//todo обробити якщо не лист пустий
        Menu.showEntities(entities);
        log.debug("\nMain menu.");
    }

    @Override
    public Optional<Entity> executeAndReturn() {
        entities = setEntities();
        if(!entities.isEmpty()) {
            int userChoice = Menu.showEntitiesAndGetAnswer(entities, "To choose the producer press the number against it.");
            return Optional.of(entities.get(userChoice));
        } else {
            return Optional.empty();
        }
    }

    protected abstract List<? extends Entity> setEntities();


}
