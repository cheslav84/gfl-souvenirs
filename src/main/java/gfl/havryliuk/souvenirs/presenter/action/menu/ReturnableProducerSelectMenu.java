package gfl.havryliuk.souvenirs.presenter.action.menu;

import gfl.havryliuk.souvenirs.entities.Entity;
import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.presenter.action.ReturnableAction;
import gfl.havryliuk.souvenirs.presenter.action.producer.DisplayAllProducers;
import gfl.havryliuk.souvenirs.presenter.action.producer.DisplayAllWithSouvenirs;
import gfl.havryliuk.souvenirs.presenter.action.producer.DisplayByPriceLessThan;
import gfl.havryliuk.souvenirs.presenter.action.producer.DisplayBySouvenirAndProductionYear;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class ReturnableProducerSelectMenu<T extends Entity> extends MenuTemplate implements ReturnableAction<Entity> {
//public class ReturnableProducerSelectMenu<Optional<T extends Entity>> extends MenuTemplate implements ReturnableAction<Optional<Entity>> {

    public List<MenuAction> getActionList() {
        return Arrays.asList(ActionList.values());
    }

    @Override
    public <T extends Entity> Optional<T> executeAndReturn() {
        actionList = getActionList();
        ReturnableAction action = (ReturnableAction) getAction(getMenuItems());
        return action.executeAndReturn();
    }

    @Getter
    @AllArgsConstructor
    private enum ActionList implements MenuAction, ReturnableAction<Entity> {
        ALL("Display all producers", new DisplayAllProducers()),
        ALL_WITH_SOUVENIRS("Display all producers with their souvenirs", new DisplayAllWithSouvenirs()),
        BY_PRICE_LESS_THAN("Display producers by price less than", new DisplayByPriceLessThan()),
        BY_SOUVENIR_AND_PRODUCTION_YEAR("Display producers by souvenir and production year", new DisplayBySouvenirAndProductionYear());

        private final String menuItem;
        private final Action action;

        public void execute() {
            action.execute();
        }

        public <T extends Entity> Optional<T> executeAndReturn() {
            ReturnableAction returnableAction;
            if (action instanceof ReturnableAction){
                returnableAction = (ReturnableAction) action;
                return returnableAction.executeAndReturn();
            }
            throw new UnsupportedOperationException();
        }

   }
}
