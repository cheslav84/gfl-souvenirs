package gfl.havryliuk.souvenirs.presenter.action.init;

import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.presenter.action.menu.ConfirmFillData;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class FillStorage implements Action {

    @Override
    public void execute() {
        log.warn("Filling storages with default data");
        log.warn("If data have been added once, it may result in a duplicate of same data!");
        new ConfirmFillData().execute();
    }


}
