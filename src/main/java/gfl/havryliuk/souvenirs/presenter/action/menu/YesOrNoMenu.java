package gfl.havryliuk.souvenirs.presenter.action.menu;

import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.presenter.action.Exit;
import gfl.havryliuk.souvenirs.presenter.action.confirm.OnConfirm;
import gfl.havryliuk.souvenirs.presenter.action.confirm.OnDeny;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class YesOrNoMenu extends MenuTemplate {

    public void setActionList() {
        actionList = ActionList.values();
    }

    @Getter
    @AllArgsConstructor
    private enum ActionList implements MenuAction {
        EXIT("End the program", new Exit()),
        CONFIRM_ACTION("YES", new OnConfirm()),
        DENY_ACTIONS("NO", new OnDeny());

        private final String description;
        private final Action action;

        public void execute() {
            action.execute();
        }
    }
}
