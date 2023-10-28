package gfl.havryliuk.souvenirs.presenter.action.producer;

import gfl.havryliuk.souvenirs.entities.Entity;
import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.presenter.action.DeleteEntity;
import gfl.havryliuk.souvenirs.presenter.action.menu.ProducerMenu;
import gfl.havryliuk.souvenirs.presenter.action.menu.ReturnableProducerSelectMenu;
import gfl.havryliuk.souvenirs.util.ConsoleReader;
import gfl.havryliuk.souvenirs.util.validation.ValidationPattern;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
public class DeleteProducer implements Action, DeleteEntity {

//    private Producer producer;




    @Override
    public void execute() {
        //select producer(List<Producer>)

        Producer producer =  (Producer) new ReturnableProducerSelectMenu().executeAndReturn();



        System.out.println(producer + " deleted");
    }

    @Override
    public void setEntity(Entity entity) {
        setProducer((Producer) entity);
    }

    private void setProducer(Producer entity) {
    }
}
