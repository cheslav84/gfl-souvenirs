package gfl.havryliuk.souvenirs.presenter.action;

import gfl.havryliuk.souvenirs.entities.Entity;

public interface ReturnableAction<T> {
    <T extends Entity> T executeAndReturn();
}
