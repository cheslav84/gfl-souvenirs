//package gfl.havryliuk.souvenirs.presenter.action.souvenir;
//
//import gfl.havryliuk.souvenirs.entities.Entity;
//import gfl.havryliuk.souvenirs.entities.Souvenir;
//import gfl.havryliuk.souvenirs.presenter.action.EntityDisplayer;
//import gfl.havryliuk.souvenirs.presenter.printer.ConsoleLoggingPrinter;
//import gfl.havryliuk.souvenirs.service.SouvenirService;
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Slf4j
//public class DisplayAllGroupedByProductionYearCopy extends EntityDisplayer<Souvenir> {
//
//    private Map<Integer,List<Souvenir>> souvenirs;
//    @Override
//    protected List<? extends Entity> setEntities() {
//        souvenirs = new SouvenirService().getGroupedByProductionYear();
//        return souvenirs.entrySet().stream()
//                .flatMap(e -> e.getValue().stream())
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    protected ConsoleLoggingPrinter<? extends Entity> setPrinter() {
//        return null;
//    }
//
//    @Override
//    public void execute() {
//        setEntities();
//        souvenirs.entrySet().forEach(this::DisplayEntry);
//        log.debug("\nMain menu.");
//    }
//
//
//    private void DisplayEntry(Map.Entry<Integer, List<Souvenir>> entry) {
//        Integer year = entry.getKey();
//        List<Souvenir> souvenirList = entry.getValue();
//        log.info("In {} was produced:", year);
//        log.info("{}", souvenirList);
//    }
//}