package gfl.havryliuk.souvenirs.presenter.action.menu;

import gfl.havryliuk.souvenirs.presenter.MenuBuilder;
import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.presenter.action.Exit;
import gfl.havryliuk.souvenirs.presenter.action.souvenir.CreateSouvenir;
import gfl.havryliuk.souvenirs.presenter.action.souvenir.DeleteSouvenir;
import gfl.havryliuk.souvenirs.presenter.action.souvenir.UpdateSouvenir;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public class SouvenirMenu implements Action, Menu {

    @Override
    public void execute() {
        String[] actions = Arrays.stream(ActionList.values())
                .map(ActionList::getName)
                .toArray(String[]::new);

        ActionList.values()[MenuBuilder.build(actions)].perform();
    }

    @Getter
    @AllArgsConstructor
    private enum ActionList {
        EXIT("End the program", new Exit()),
        CREATE_SOUVENIR("Create producer", new CreateSouvenir()),
        UPDATE_SOUVENIR("Update producer", new UpdateSouvenir()),
        DELETE_SOUVENIR("Delete producer", new DeleteSouvenir()),
        SELECT_OPTIONS("Select options", new DeleteSouvenir()),//TODO
        MAIN_MENU("Main menu", new MainMenu());

        private final String name;
        private final Action action;

        public void perform() {
            action.execute();
        }
    }
}
