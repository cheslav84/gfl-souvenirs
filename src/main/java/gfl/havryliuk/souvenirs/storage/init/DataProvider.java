package gfl.havryliuk.souvenirs.storage.init;

import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.entities.Souvenir;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DataProvider {

    public List<Producer> getInitialData() {
        List<Producer> producers = new ArrayList<>();

        Producer spell = new Producer("Spell", "Бельгія");
        List<Souvenir> spellSouvenirs = new ArrayList<>();
        spellSouvenirs.add(new Souvenir("Набір цукерок \"Шоколадна вишиванка\"",99,
                LocalDateTime.parse("2023-09-30T00:00:00"), spell));
        spellSouvenirs.add(new Souvenir("Набір цукерок \"Асорті трюфелів\"",149,
                LocalDateTime.parse("2023-07-04T00:00:00"), spell));
        spellSouvenirs.add(new Souvenir("Малиновий шоколад з фісташковою пастою",99,
                LocalDateTime.parse("2023-06-23T00:00:00"), spell));
        spellSouvenirs.add(new Souvenir("Шоколадна сніжинка",139,
                LocalDateTime.parse("2023-08-15T00:00:00"), spell));
        spell.setSouvenirs(spellSouvenirs);
        producers.add(spell);


        Producer folkmart = new Producer("Folkmart", "Україна");
        List<Souvenir> folkmarts = new ArrayList<>();
        folkmarts.add(new Souvenir("Горнятко \"Колаж (біла графіка)\"",325,
                LocalDateTime.parse("2022-04-04T00:00:00"), folkmart));
        folkmarts.add(new Souvenir("Горнятко \"Сучасне місто\"",3950,
                LocalDateTime.parse("2022-04-21T00:00:00"), folkmart));
        folkmarts.add(new Souvenir("Булава сувенірна",99,
                LocalDateTime.parse("2021-10-07T00:00:00"), folkmart));
        folkmarts.add(new Souvenir("Книга - Ілюстрована історія України (Грушевский М.С.)",139,
                LocalDateTime.parse("2023-08-15T00:00:00"), folkmart));
        folkmart.setSouvenirs(folkmarts);
        producers.add(folkmart);


        Producer souvenirUA = new Producer("SouvenirUA", "Україна");
        List<Souvenir> souvenirUAs = new ArrayList<>();
        souvenirUAs.add(new Souvenir("Горнятко \"Орнамент\"",180 ,
                LocalDateTime.parse("2021-04-21T00:00:00"), souvenirUA));
        souvenirUAs.add(new Souvenir("Горнятко \"Колаж (біла графіка)\"",180,
                LocalDateTime.parse("2020-04-04T00:00:00"), souvenirUA));
        souvenirUAs.add(new Souvenir("Прапор України габардиновий з тризубом",485,
                LocalDateTime.parse("2019-02-10T00:00:00"), souvenirUA));
        souvenirUA.setSouvenirs(souvenirUAs);
        producers.add(souvenirUA);


        return producers;
    }

}




