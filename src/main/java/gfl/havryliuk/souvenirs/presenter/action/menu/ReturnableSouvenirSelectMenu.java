package gfl.havryliuk.souvenirs.presenter.action.menu;

import gfl.havryliuk.souvenirs.entities.Souvenir;
import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.presenter.action.ReturnableAction;
import gfl.havryliuk.souvenirs.presenter.action.souvenir.DisplayAllGroupedByProductionYear;
import gfl.havryliuk.souvenirs.presenter.action.souvenir.DisplayAllSouvenirs;
import gfl.havryliuk.souvenirs.presenter.action.souvenir.DisplayByProducer;
import gfl.havryliuk.souvenirs.presenter.action.souvenir.DisplayByProductionCountry;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class ReturnableSouvenirSelectMenu extends MenuTemplate implements ReturnableAction<Souvenir>{

    public List<MenuAction> getActionList() {
        return Arrays.asList(ActionList.values());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<Souvenir> executeAndReturn() {
        actionList = getActionList();
        ReturnableAction<Souvenir> action = (ReturnableAction<Souvenir>) getAction(getMenuItems());
        return action.executeAndReturn();
    }

    @Getter
    @AllArgsConstructor
    private enum ActionList implements MenuAction, ReturnableAction<Souvenir> {
        ALL("Display all souvenirs", new DisplayAllSouvenirs()),
        ALL_GROUPED_BY_PRODUCTION_YEAR("Display all souvenirs grouped by production year", new DisplayAllGroupedByProductionYear()),
        BY_PRODUCER("Display souvenirs by producer", new DisplayByProducer()),
        BY_PRODUCTION_COUNTRY("Display souvenirs by production country", new DisplayByProductionCountry());

        private final String menuItem;
        private final Action action;

        public void execute() {
            action.execute();
        }

        @SuppressWarnings("unchecked")
        public Optional<Souvenir> executeAndReturn() {
            ReturnableAction<Souvenir> returnableAction;
            if (action instanceof ReturnableAction){
                returnableAction = (ReturnableAction<Souvenir>) action;
                return returnableAction.executeAndReturn();
            }
            throw new UnsupportedOperationException();
        }

   }
}
