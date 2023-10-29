package gfl.havryliuk.souvenirs.presenter.action.producer;

import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.presenter.action.menu.ReturnableProducerSelectMenu;
import gfl.havryliuk.souvenirs.service.ProducerService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
public class DeleteProducer implements Action {
    @Override
    public void execute() {
        Producer producer = new ReturnableProducerSelectMenu<Producer>().executeAndReturn();
        ProducerService service = new ProducerService();
        service.delete(producer);
        log.info("{} removed.", producer);
    }
}
