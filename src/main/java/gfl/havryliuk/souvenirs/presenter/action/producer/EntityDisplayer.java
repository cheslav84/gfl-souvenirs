package gfl.havryliuk.souvenirs.presenter.action.producer;

import gfl.havryliuk.souvenirs.entities.Entity;
import gfl.havryliuk.souvenirs.presenter.Menu;
import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.presenter.action.ReturnableAction;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

    @Slf4j
    @Setter
    public abstract class EntityDisplayer implements Action, ReturnableAction<Entity> {

    protected List<? extends Entity> entities;

    @Override
    public void execute() {
        entities = setEntities();//todo обробити якщо не лист пустий
        Menu.showEntities(entities);
        log.debug("\nMain menu.");
    }

    @Override
    public Entity executeAndReturn() {
        entities = setEntities();//todo обробити якщо не лист пустий
        int userChoice = Menu.showEntitiesAndGetAnswer(entities, "To choose the producer press the number against it.");
        return entities.get(userChoice);
    }

    protected abstract List<? extends Entity> setEntities();


}
