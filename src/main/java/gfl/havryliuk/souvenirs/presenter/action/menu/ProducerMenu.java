package gfl.havryliuk.souvenirs.presenter.action.menu;

import gfl.havryliuk.souvenirs.presenter.action.*;
import gfl.havryliuk.souvenirs.presenter.action.producer.CreateProducer;
import gfl.havryliuk.souvenirs.presenter.action.producer.DeleteProducer;
import gfl.havryliuk.souvenirs.presenter.action.producer.UpdateProducer;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;


public class ProducerMenu extends MenuTemplate {

    public List<MenuAction> getActionList() {
        return Arrays.asList(ActionList.values());
    }

    @Getter
    @AllArgsConstructor
    private enum ActionList implements MenuAction {
        EXIT("End the program", new Exit()),
        CREATE_PRODUCER("Create producer", new CreateProducer()),
        UPDATE_PRODUCER("Update producer", new UpdateProducer()),
        DELETE_PRODUCER("Delete producer", new DeleteProducer()),
        SELECT_OPTIONS("Select options", new ProducerSelectMenu()),
        BACK("Back to main menu", new EmptyAction());

        private final String menuItem;
        private final Action action;

        public void execute() {
            action.execute();
        }

    }
}
