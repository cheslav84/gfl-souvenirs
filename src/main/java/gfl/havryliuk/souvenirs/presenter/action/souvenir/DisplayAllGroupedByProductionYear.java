package gfl.havryliuk.souvenirs.presenter.action.souvenir;

import gfl.havryliuk.souvenirs.entities.Entity;
import gfl.havryliuk.souvenirs.entities.Souvenir;
import gfl.havryliuk.souvenirs.presenter.Menu;
import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.presenter.action.EntityDisplayer;
import gfl.havryliuk.souvenirs.service.SouvenirService;
import gfl.havryliuk.souvenirs.util.ConsoleReader;
import gfl.havryliuk.souvenirs.util.validation.ValidationPattern;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class DisplayAllGroupedByProductionYear extends EntityDisplayer {

    private Map<Integer,List<Souvenir>> souvenirs;
    @Override
    protected List<? extends Entity> setEntities() {
        souvenirs = new SouvenirService().getGroupedByProductionYear();
        return souvenirs.entrySet().stream()
                .flatMap(e -> e.getValue().stream())
                .collect(Collectors.toList());
    }

    @Override
    public void execute() {
        setEntities();
        souvenirs.entrySet().forEach(this::DisplayEntry);
        log.debug("\nMain menu.");
    }


    private void DisplayEntry(Map.Entry<Integer, List<Souvenir>> entry) {
        Integer year = entry.getKey();
        List<Souvenir> souvenirList = entry.getValue();
        log.info("In {} was produced:", year);
        log.info("{}", souvenirList);
    }
}
