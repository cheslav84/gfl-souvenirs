package gfl.havryliuk.souvenirs.presenter.action.menu;

import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.presenter.action.Exit;
import gfl.havryliuk.souvenirs.presenter.MenuBuilder;
import gfl.havryliuk.souvenirs.presenter.action.confirm.OnConfirm;
import gfl.havryliuk.souvenirs.presenter.action.confirm.OnDeny;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

public class YesOrNoMenu implements Action, Menu {
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
        CONFIRM_ACTION("YES", new OnConfirm()),
        DENY_ACTIONS("NO", new OnDeny());

        private final String name;
        private final Action action;

        public void perform() {
            action.execute();
        }

    }
}
