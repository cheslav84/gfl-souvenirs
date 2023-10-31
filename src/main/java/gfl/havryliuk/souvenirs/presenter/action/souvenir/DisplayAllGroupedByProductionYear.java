package gfl.havryliuk.souvenirs.presenter.action.souvenir;

import gfl.havryliuk.souvenirs.entities.Entity;
import gfl.havryliuk.souvenirs.entities.Souvenir;
import gfl.havryliuk.souvenirs.entities.dto.SouvenirsByYearDto;
import gfl.havryliuk.souvenirs.presenter.MenuDisplayer;
import gfl.havryliuk.souvenirs.presenter.action.EntityDisplayer;
import gfl.havryliuk.souvenirs.presenter.printer.ConsoleLoggingPrinter;
import gfl.havryliuk.souvenirs.presenter.printer.SouvenirsByYearPrinter;
import gfl.havryliuk.souvenirs.service.SouvenirService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class DisplayAllGroupedByProductionYear extends EntityDisplayer<SouvenirsByYearDto> {

    @Override
    protected List<SouvenirsByYearDto> setEntities() {
        return new SouvenirService().getGroupedByProductionYear();
    }

    @Override
    protected ConsoleLoggingPrinter<SouvenirsByYearDto> setPrinter() {
        return new SouvenirsByYearPrinter<>(entities);
    }


    @Override
    public Optional<Entity> executeAndReturn() {
        entities = setEntities();
        List<SouvenirsByYearDto> dto = (List<SouvenirsByYearDto>)entities;
        List<Souvenir> souvenirs = dto.stream()
                .flatMap(ls -> ls.getSouvenirs().stream())
                .collect(Collectors.toList());
        if(!souvenirs.isEmpty()) {
            int userChoice = MenuDisplayer.showEntitiesAndGetAnswer(souvenirs,
                    "To choose the producer press the number against it.");
            return Optional.of(souvenirs.get(userChoice));
        } else {
            return Optional.empty();
        }
    }

}
