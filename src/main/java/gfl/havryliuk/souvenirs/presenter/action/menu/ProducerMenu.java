package gfl.havryliuk.souvenirs.presenter.action.menu;

import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.presenter.action.Exit;
import gfl.havryliuk.souvenirs.presenter.MenuBuilder;
import gfl.havryliuk.souvenirs.presenter.action.producer.CreateProducer;
import gfl.havryliuk.souvenirs.presenter.action.producer.DeleteProducer;
import gfl.havryliuk.souvenirs.presenter.action.producer.UpdateProducer;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

public class ProducerMenu implements Action, Menu {
    @Override
    public void execute() {
        String[] actionNames = Arrays.stream(ActionList.values())
                .map(ActionList::getName)
                .toArray(String[]::new);

        ActionList.values()[MenuBuilder.build(actionNames)].perform();
    }

    @Getter
    @AllArgsConstructor
    private enum ActionList {
        EXIT("End the program", new Exit()),
        CREATE_PRODUCER("Create producer", new CreateProducer()),
        UPDATE_PRODUCER("Update producer", new UpdateProducer()),
        DELETE_PRODUCER("Delete producer", new DeleteProducer()),
        SELECT_OPTIONS("Select options", new DeleteProducer()),
        MAIN_MENU("Main menu", new MainMenu());

        private final String name;
        private final Action action;

        public void perform() {
            action.execute();
        }

    }
}
