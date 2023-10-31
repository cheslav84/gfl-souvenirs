package gfl.havryliuk.souvenirs.presenter.action.souvenir;

import gfl.havryliuk.souvenirs.entities.Entity;
import gfl.havryliuk.souvenirs.entities.Souvenir;
import gfl.havryliuk.souvenirs.presenter.action.EntityDisplayer;
import gfl.havryliuk.souvenirs.presenter.printer.ConsoleLoggingPrinter;
import gfl.havryliuk.souvenirs.presenter.printer.SouvenirPrinter;
import gfl.havryliuk.souvenirs.service.SouvenirService;
import gfl.havryliuk.souvenirs.util.ConsoleReader;
import gfl.havryliuk.souvenirs.util.validation.ValidationPattern;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class DisplayByProducer extends EntityDisplayer<Souvenir> {

    @Override
    protected List<Souvenir> setEntities() {
        String name = ConsoleReader.readString("producer name", ValidationPattern.NAME);
        String country = ConsoleReader.readString("producer country", ValidationPattern.COUNTRY);
        return new SouvenirService().getByProducerNameAndCountry(name, country);
    }

    @Override
    protected ConsoleLoggingPrinter<Souvenir> setPrinter() {
        return new SouvenirPrinter<>(entities);
    }
}
