package gfl.havryliuk.souvenirs.presenter.action.producer;

import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.presenter.action.menu.ReturnableProducerSelectMenu;
import gfl.havryliuk.souvenirs.service.ProducerService;
import gfl.havryliuk.souvenirs.util.ConsoleReader;
import gfl.havryliuk.souvenirs.util.validation.ValidationPattern;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@Setter
public class UpdateProducer implements Action {

    @Override
    public void execute() {
        Optional<Producer> producerOptional = new ReturnableProducerSelectMenu<Producer>().executeAndReturn();
        if (producerOptional.isPresent()) {
            Producer producer = producerOptional.get();

            String name = ConsoleReader.readForUpdatingString(producer.getName(), "producer name", ValidationPattern.NAME);
            String country = ConsoleReader.readForUpdatingString(producer.getCountry(), "country", ValidationPattern.COUNTRY);

            producer.setName(name);
            producer.setCountry(country);

            ProducerService service = new ProducerService();
            service.update(producer);
            log.info("{} updated.", producer);
        } else {
            log.info("Producer hasn't been found.");
        }
    }
}
