package gfl.havryliuk.souvenirs.util.init;

import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.entities.Souvenir;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DataProvider {

    public List<Producer> createInitialData() {
        List<Producer> producers = new ArrayList<>();

        Producer spell = new Producer("Spell", "Бельгія");
        List<Souvenir> spellSouvenirs = new ArrayList<>();
        spellSouvenirs.add(new Souvenir("Набір цукерок \\\"Шоколадна вишиванка\\\"",99,  LocalDateTime.parse("2023-09-30T00:00:00"), spell));
        spellSouvenirs.add(new Souvenir("Набір цукерок \\\"Асорті трюфелів\\\"",149,  LocalDateTime.parse("2023-07-04T00:00:00"), spell));
        spellSouvenirs.add(new Souvenir("Малиновий шоколад з фісташковою пастою",99,  LocalDateTime.parse("2023-06-23T00:00:00"), spell));
        spellSouvenirs.add(new Souvenir("Шоколадна сніжинка",139,  LocalDateTime.parse("2023-08-15T00:00:00"), spell));
        spell.setSouvenirs(spellSouvenirs);
        producers.add(spell);



//        Producer spell1 = new Producer("Spell", "Україна");
//        List<Souvenir> souvenirs1 = new ArrayList<>();
//        souvenirs1.add(new Souvenir("Набір цукерок \\\"Шоколадна вишиванка\\\"",99,  LocalDateTime.parse("2023-09-30T00:00:00"), spell1));
//        souvenirs1.add(new Souvenir("Набір цукерок \\\"Асорті трюфелів\\\"",149,  LocalDateTime.parse("2023-07-04T00:00:00"), spell1));
//        souvenirs1.add(new Souvenir("Малиновий шоколад з фісташковою пастою",99,  LocalDateTime.parse("2023-06-23T00:00:00"), spell1));
//        souvenirs1.add(new Souvenir("Шоколадна сніжинка",139,  LocalDateTime.parse("2023-08-15T00:00:00"), spell1));
//        spell1.setSouvenirs(souvenirs1);
        return producers;
    }

}
