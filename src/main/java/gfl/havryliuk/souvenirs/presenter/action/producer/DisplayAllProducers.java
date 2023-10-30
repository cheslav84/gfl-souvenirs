package gfl.havryliuk.souvenirs.presenter.action.producer;

import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.presenter.action.EntityDisplayer;
import gfl.havryliuk.souvenirs.presenter.printer.ConsoleLoggingPrinter;
import gfl.havryliuk.souvenirs.presenter.printer.ProducerPrinter;
import gfl.havryliuk.souvenirs.service.ProducerService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class DisplayAllProducers extends EntityDisplayer<Producer> {

    @Override
    protected List<Producer> setEntities() {
        return new ProducerService().getAll();
    }

    @Override
    protected ConsoleLoggingPrinter<Producer> setPrinter() {
        return new ProducerPrinter<>(entities);
    }
}
