package gfl.havryliuk.souvenirs.presenter.action.producer;

import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.presenter.Action;
import gfl.havryliuk.souvenirs.presenter.menu.ConsoleReader;
import gfl.havryliuk.souvenirs.util.validation.ValidationPattern;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreateProducer implements Action {

    @Override
    public void execute() {
        String name = ConsoleReader.readString("producer name", ValidationPattern.NAME);
        String country = ConsoleReader.readString("country", ValidationPattern.COUNTRY);

        Producer producer = new Producer(name, country);
        log.info("{} created.", producer);
        new ProducerActions().execute();
    }
}
