package gfl.havryliuk.souvenirs.presenter.action.menu;

import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.presenter.action.Exit;
import gfl.havryliuk.souvenirs.presenter.action.producer.CreateProducer;
import gfl.havryliuk.souvenirs.presenter.action.producer.DeleteProducer;
import gfl.havryliuk.souvenirs.presenter.action.producer.UpdateProducer;
import lombok.AllArgsConstructor;
import lombok.Getter;


public class ProducerMenu extends MenuTemplate {

    public void setActionList() {
        actionList = ActionList.values();
    }

    @Getter
    @AllArgsConstructor
    private enum ActionList implements MenuAction {
        EXIT("End the program", new Exit()),
        CREATE_PRODUCER("Create producer", new CreateProducer()),
        UPDATE_PRODUCER("Update producer", new UpdateProducer()),
        DELETE_PRODUCER("Delete producer", new DeleteProducer()),
        SELECT_OPTIONS("Select options", new DeleteProducer()),
        MAIN_MENU("Main menu", new MainMenu());

        private final String description;
        private final Action action;

        public void execute() {
            action.execute();
        }

    }
}
