//package gfl.havryliuk.souvenirs.presenter;
//
//import gfl.havryliuk.souvenirs.presenter.action.producer.CreateProducer;
//import gfl.havryliuk.souvenirs.presenter.action.producer.DeleteProducer;
//import gfl.havryliuk.souvenirs.presenter.action.Exit;
//import gfl.havryliuk.souvenirs.presenter.action.producer.UpdateProducer;
//import gfl.havryliuk.souvenirs.presenter.action.souvenir.CreateSouvenir;
//import gfl.havryliuk.souvenirs.presenter.action.souvenir.DeleteSouvenir;
//import gfl.havryliuk.souvenirs.presenter.action.souvenir.UpdateSouvenir;
//import gfl.havryliuk.souvenirs.service.Service;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//
//import java.util.Arrays;
//
//@Getter
//@AllArgsConstructor
//public enum ActionList {
//    EXIT("End the program", new Exit()),
//    CREATE_PRODUCER("Create producer", new CreateProducer()),
//    UPDATE_PRODUCER("Update producer", new UpdateProducer()),
//    DELETE_PRODUCER("Delete producer", new DeleteProducer()),
//    CREATE_SOUVENIR("Create souvenir", new CreateSouvenir()),
//    UPDATE_SOUVENIR("Update souvenir", new UpdateSouvenir()),
//    DELETE_SOUVENIR("Delete souvenir", new DeleteSouvenir());
//
//
//
//    private final String name;
//    private final Action action;
//
//    public void execute() {
//        action.execute();
//    }
//
//    public static String[] mapAction() {
//        return Arrays.stream(ActionList.values())
//                .map(ActionList::getName)
//                .toArray(String[]::new);
//    }
//}
