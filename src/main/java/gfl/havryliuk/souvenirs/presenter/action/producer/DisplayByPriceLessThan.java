package gfl.havryliuk.souvenirs.presenter.action.producer;

import gfl.havryliuk.souvenirs.entities.Entity;

import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.presenter.action.EntityDisplayer;
import gfl.havryliuk.souvenirs.presenter.printer.ConsoleLoggingPrinter;
import gfl.havryliuk.souvenirs.presenter.printer.ProducerPrinter;
import gfl.havryliuk.souvenirs.service.ProducerService;
import gfl.havryliuk.souvenirs.util.ConsoleReader;
import gfl.havryliuk.souvenirs.util.validation.ValidationPattern;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class DisplayByPriceLessThan extends EntityDisplayer<Producer> {

    @Override
    protected List<? extends Entity> setEntities() {
        double price = ConsoleReader.readDouble("the price", ValidationPattern.PRICE);
        return new ProducerService().getByPriceLessThan(price);
    }

    @Override
    protected ConsoleLoggingPrinter<Producer> setPrinter() {
        return new ProducerPrinter<>(entities);
    }
}
