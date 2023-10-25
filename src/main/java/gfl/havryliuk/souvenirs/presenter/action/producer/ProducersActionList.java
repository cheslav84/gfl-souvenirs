package gfl.havryliuk.souvenirs.presenter.action.producer;

import gfl.havryliuk.souvenirs.presenter.Action;
import gfl.havryliuk.souvenirs.presenter.action.Exit;
import gfl.havryliuk.souvenirs.presenter.action.MainAction;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ProducersActionList {
    EXIT("End the program", new Exit()),
    CREATE_PRODUCER("Create producer", new CreateProducer()),
    UPDATE_PRODUCER("Update producer", new UpdateProducer()),
    DELETE_PRODUCER("Delete producer", new DeleteProducer()),
    SELECT_OPTIONS("Select options", new DeleteProducer()),
    MAIN_MENU("Main menu", new MainAction());

    private final String name;
    private final Action action;

    public void execute() {
        action.execute();
    }

    public static String[] mapAction() {
        return Arrays.stream(ProducersActionList.values())
                .map(ProducersActionList::getName)
                .toArray(String[]::new);
    }
}
