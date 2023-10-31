package gfl.havryliuk.souvenirs;

import gfl.havryliuk.souvenirs.presenter.action.menu.MainMenu;
import gfl.havryliuk.souvenirs.presenter.action.producer.DisplayAllProducersWithSouvenirs;
import gfl.havryliuk.souvenirs.presenter.action.souvenir.DisplayAllGroupedByProductionYear;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class Main {


    public static void main(String[] args) {
        log.warn("Welcome to souvenir store!");

        new DisplayAllGroupedByProductionYear().execute();
//        new DisplayAllProducersWithSouvenirs().execute();
//        new DisplayAllProducers().execute();
//        new DisplayAllSouvenirs().execute();
//        new DisplayAllWithSouvenirs().execute();
//        new DeleteProducer().execute();


//        try {
////            new InitMenu().execute();
//            while (true) {
//                new MainMenu().execute();
//            }
//        } catch (Exception e) {
//            showMessage();
//        }

//        List<Producer> producers = new ProducerService().getAll();
//        ConsoleLoggingPrinter<Producer> printer = new ProducerPrinter<>(producers);
////
//        String collect = producers.stream()
//                .collect(printer.entitiesCollector());
//
//        log.info("{}", collect);

//        System.out.println(collect);
    }

    private static void showMessage() {
        log.warn("""
                An error have occurred.\s
                The developer is deeply concern about this and will fix one as soon as possible!\s
                Try please again.""");
        System.exit(0);
    }


}
