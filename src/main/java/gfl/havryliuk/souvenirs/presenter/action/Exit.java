package gfl.havryliuk.souvenirs.presenter.action;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Exit implements Action {

    @Override
    public void execute() {
        log.warn("See you later!");
        System.exit(0);
    }
}
