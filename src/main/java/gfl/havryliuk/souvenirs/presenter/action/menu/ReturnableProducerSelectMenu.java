package gfl.havryliuk.souvenirs.presenter.action.menu;

import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.presenter.action.EmptyAction;
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


public class ReturnableProducerSelectMenu extends MenuTemplate implements ReturnableAction<Producer>{

    public List<MenuAction> getActionList() {
        return Arrays.asList(ActionList.values());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<Producer> executeAndReturn() {
        actionList = getActionList();
        ReturnableAction<Producer> action = (ReturnableAction<Producer>) getAction(getMenuItems());
        return action.executeAndReturn();
    }

    @Getter
    @AllArgsConstructor
    private enum ActionList implements MenuAction, ReturnableAction<Producer> {
        ALL("Display all producers", new DisplayAllProducers()),
        ALL_WITH_SOUVENIRS("Display all producers with their souvenirs", new DisplayAllWithSouvenirs()),
        BY_PRICE_LESS_THAN("Display producers by price less than", new DisplayByPriceLessThan()),
        BY_SOUVENIR_AND_PRODUCTION_YEAR("Display producers by souvenir and production year", new DisplayBySouvenirAndProductionYear());

        private final String menuItem;
        private final Action action;

        public void execute() {
            action.execute();
        }

        @SuppressWarnings("unchecked")
        public Optional<Producer> executeAndReturn() {
            ReturnableAction<Producer> returnableAction;
            if (action instanceof ReturnableAction){
                returnableAction = (ReturnableAction<Producer>) action;
                return returnableAction.executeAndReturn();
            }
            throw new UnsupportedOperationException();
        }

   }
}
