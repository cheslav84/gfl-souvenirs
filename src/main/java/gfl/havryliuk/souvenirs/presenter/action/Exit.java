package gfl.havryliuk.souvenirs.presenter.action;

import gfl.havryliuk.souvenirs.presenter.Action;
import gfl.havryliuk.souvenirs.service.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Exit implements Action {

    @Override
    public void execute() {
        log.warn("See you later!");
        System.exit(0);
    }
}
