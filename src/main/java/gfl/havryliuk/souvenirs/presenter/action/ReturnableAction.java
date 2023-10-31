package gfl.havryliuk.souvenirs.presenter.action;

import gfl.havryliuk.souvenirs.entities.Entity;

import java.util.Optional;

public interface ReturnableAction<T extends Entity> {
    Optional<T> executeAndReturn();
}
