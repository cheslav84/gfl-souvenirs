package gfl.havryliuk.souvenirs.presenter.action;

import gfl.havryliuk.souvenirs.entities.Entity;

public interface DeleteEntity extends Action {

    void execute();

    void setEntity(Entity entity);
}
