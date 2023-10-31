package gfl.havryliuk.souvenirs.presenter.action.producer;

import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.presenter.MenuDisplayer;
import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.presenter.action.menu.ReturnableProducerSelectMenu;
import gfl.havryliuk.souvenirs.service.ProducerService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@Setter
public class DeleteProducer implements Action {
    @Override
    public void execute() {
        Optional<Producer> producerOptional = new ReturnableProducerSelectMenu().executeAndReturn();
        if (producerOptional.isPresent()) {
            Producer producer = producerOptional.get();
            if (isRemovingConfirmed(producer)) {
                new ProducerService().delete(producer);
                log.info("{} removed.", producer);
            } else  {
                log.info("Removing {} canceled.", producer);
            }

        } else {
            log.warn("Producer hasn't been found.");
        }
    }

    private boolean isRemovingConfirmed(Producer producer) {
        log.warn("Do you really want to remove {}?", producer);
        List<String> menuItems = List.of("no", "yes");
        return MenuDisplayer.showItemsAndGetAnswer(menuItems) == 1;
    }


}
