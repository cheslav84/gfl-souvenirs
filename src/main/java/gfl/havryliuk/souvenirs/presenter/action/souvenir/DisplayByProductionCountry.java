package gfl.havryliuk.souvenirs.presenter.action.souvenir;

import gfl.havryliuk.souvenirs.entities.Entity;
import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.presenter.action.EntityDisplayer;
import gfl.havryliuk.souvenirs.service.SouvenirService;
import gfl.havryliuk.souvenirs.util.ConsoleReader;
import gfl.havryliuk.souvenirs.util.validation.ValidationPattern;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class DisplayByProductionCountry extends EntityDisplayer {

    @Override
    protected List<? extends Entity> setEntities() {
        String country = ConsoleReader.readString("producer country", ValidationPattern.COUNTRY);
        return new SouvenirService().getByProducerCountry(country);
    }
}
