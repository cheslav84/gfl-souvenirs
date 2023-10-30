package gfl.havryliuk.souvenirs.presenter.action.producer;

import gfl.havryliuk.souvenirs.entities.Entity;
import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.presenter.action.EntityDisplayer;
import gfl.havryliuk.souvenirs.presenter.printer.ConsoleLoggingPrinter;
import gfl.havryliuk.souvenirs.presenter.printer.ProducerWithSouvenirsPrinter;
import gfl.havryliuk.souvenirs.service.ProducerService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Slf4j
public class DisplayAllProducersWithSouvenirs extends EntityDisplayer<Producer> {

    @Override
    protected List<? extends Entity> setEntities() {
        return  new ProducerService().getAllWithSouvenirs();
    }

    @Override
    protected ConsoleLoggingPrinter<Producer> setPrinter() {
        return new ProducerWithSouvenirsPrinter<>(entities);
    }


}