package gfl.havryliuk.souvenirs.presenter.action.souvenir;

import gfl.havryliuk.souvenirs.entities.Entity;
import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.entities.Souvenir;
import gfl.havryliuk.souvenirs.presenter.action.EntityDisplayer;
import gfl.havryliuk.souvenirs.presenter.printer.ConsoleLoggingPrinter;
import gfl.havryliuk.souvenirs.presenter.printer.ProducerPrinter;
import gfl.havryliuk.souvenirs.presenter.printer.SouvenirPrinter;
import gfl.havryliuk.souvenirs.service.SouvenirService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class DisplayAllSouvenirs extends EntityDisplayer<Souvenir> {

    @Override
    protected List<? extends Entity> setEntities() {
        return new SouvenirService().getAll();
    }

    @Override
    protected ConsoleLoggingPrinter<Souvenir> setPrinter() {
        return new SouvenirPrinter<>(entities);
    }
}
