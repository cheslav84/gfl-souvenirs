package gfl.havryliuk.souvenirs.presenter.action.producer;

import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.presenter.action.menu.ProducerMenu;
import gfl.havryliuk.souvenirs.service.ProducerService;
import gfl.havryliuk.souvenirs.util.ConsoleReader;
import gfl.havryliuk.souvenirs.util.validation.ValidationPattern;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreateProducer implements Action {

    @Override
    public void execute() {
        String name = ConsoleReader.readString("producer name", ValidationPattern.NAME);
        String country = ConsoleReader.readString("country", ValidationPattern.COUNTRY);
        Producer producer = new Producer(name, country);
        ProducerService service = new ProducerService();
        service.create(producer);
        log.info("{} created.", producer);
    }
}
