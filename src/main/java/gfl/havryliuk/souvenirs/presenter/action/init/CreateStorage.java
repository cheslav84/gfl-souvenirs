package gfl.havryliuk.souvenirs.presenter.action.init;

import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.presenter.action.menu.InitMenu;
import gfl.havryliuk.souvenirs.presenter.action.menu.ConfirmCreateStorages;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreateStorage implements Action {

    @Override
    public void execute() {
        log.debug("Creating new storage.");
        log.warn("If storages already exist, it may cause them to be overwritten and all data will be lost!");
        new ConfirmCreateStorages().execute();
        new InitMenu().execute();
    }
}
