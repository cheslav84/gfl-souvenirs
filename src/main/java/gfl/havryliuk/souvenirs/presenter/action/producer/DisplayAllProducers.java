package gfl.havryliuk.souvenirs.presenter.action.producer;

import gfl.havryliuk.souvenirs.entities.Entity;
import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.presenter.action.UpdateEntity;
import gfl.havryliuk.souvenirs.presenter.action.menu.DisplayEntitiesMenu;
import gfl.havryliuk.souvenirs.service.ProducerService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class DisplayAllProducers extends DisplayEntitiesMenu {

    public List<Producer> getEntities() {
        ProducerService service = new ProducerService();
        return service.getAll();//todo подумати, тут відрізнятиметься від інших
    }

    public UpdateEntity getUpdateAction(Entity entity) {
        UpdateEntity updateAction = new UpdateEntity();
        updateAction.setProducer((Producer) entity);
        return updateAction;
    }

    public DeleteProducer getDeleteAction(Entity entity) {
        DeleteProducer deleteAction = new DeleteProducer();
        deleteAction.setEntity(entity);
        return deleteAction;
    }

}
