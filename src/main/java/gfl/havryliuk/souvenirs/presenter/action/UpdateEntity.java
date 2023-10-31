package gfl.havryliuk.souvenirs.presenter.action;

import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.presenter.action.Action;
import lombok.Setter;

@Setter
public class UpdateEntity implements Action {
    private Producer producer;
    @Override
    public void execute() {
        System.out.println(producer + " updated");

    }
}
