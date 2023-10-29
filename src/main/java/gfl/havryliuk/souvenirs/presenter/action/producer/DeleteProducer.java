package gfl.havryliuk.souvenirs.presenter.action.producer;

import gfl.havryliuk.souvenirs.entities.Entity;
import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.presenter.action.menu.ReturnableProducerSelectMenu;
import gfl.havryliuk.souvenirs.service.ProducerService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@Setter
public class DeleteProducer implements Action {
    @Override
    public void execute() {
        Optional<Producer> producerOptional = new ReturnableProducerSelectMenu<Producer>().executeAndReturn();
        if (producerOptional.isPresent()) {
            Producer producer = producerOptional.get();
            ProducerService service = new ProducerService();
            service.delete(producer);
            log.info("{} removed.", producer);
        } else {
            log.info("Producer hasn't been found.");
        }
    }
}
