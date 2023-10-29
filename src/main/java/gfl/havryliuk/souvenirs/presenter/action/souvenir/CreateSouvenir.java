package gfl.havryliuk.souvenirs.presenter.action.souvenir;

import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.entities.Souvenir;
import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.presenter.action.menu.ReturnableProducerSelectMenu;
import gfl.havryliuk.souvenirs.service.SouvenirService;
import gfl.havryliuk.souvenirs.util.ConsoleReader;
import gfl.havryliuk.souvenirs.util.validation.ValidationPattern;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
public class CreateSouvenir implements Action {
    @Override
    public void execute() {
        Optional<Producer> producerOptional = new ReturnableProducerSelectMenu().executeAndReturn();
        if (producerOptional.isPresent()) {
            Producer producer = producerOptional.get();

            String name = ConsoleReader.readString("souvenir name", ValidationPattern.NAME);
            double price = ConsoleReader.readDouble("the price", ValidationPattern.PRICE);
            String datePatter = ConsoleReader.readString("production date in pattern yyyy-mm-dd",
                    ValidationPattern.DATE);
            LocalDateTime productionDate = LocalDateTime.parse(datePatter + "T00:00:00");

            Souvenir souvenir = new Souvenir(name, price, productionDate, producer);
            SouvenirService service = new SouvenirService();
            service.create(souvenir);//todo add souvenir to producer
            log.info("{} created.", souvenir);

        } else {
            log.warn("Producer hasn't been found.");
        }


    }
}
