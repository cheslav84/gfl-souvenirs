package gfl.havryliuk.souvenirs.testDataProvider;

import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.entities.Souvenir;

import java.util.UUID;

public class SouvenirProvider {

    public static Souvenir getSouvenirWithOnlyId() {
        Souvenir souvenir = new Souvenir();
        souvenir.setId(UUID.randomUUID());
        return souvenir;
    }


}
