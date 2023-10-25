package gfl.havryliuk.souvenirs.presenter;

import gfl.havryliuk.souvenirs.presenter.action.Exit;
import gfl.havryliuk.souvenirs.presenter.action.producer.ProducerActions;
import gfl.havryliuk.souvenirs.presenter.action.producer.UpdateProducer;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum MainActionList {
    EXIT("End the program", new Exit()),
    PRODUCER_ACTIONS("Producers", new ProducerActions()),
    SOUVENIR_ACTIONS("Souvenirs", new UpdateProducer());


    private final String name;
    private final Action action;

    public void execute() {
        action.execute();
    }

    public static String[] mapAction() {
        return Arrays.stream(MainActionList.values())
                .map(MainActionList::getName)
                .toArray(String[]::new);
    }
}
