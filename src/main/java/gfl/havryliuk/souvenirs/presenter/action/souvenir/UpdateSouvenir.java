package gfl.havryliuk.souvenirs.presenter.action.souvenir;

import gfl.havryliuk.souvenirs.entities.Souvenir;
import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.presenter.action.menu.ReturnableSouvenirSelectMenu;
import gfl.havryliuk.souvenirs.service.ProducerService;
import gfl.havryliuk.souvenirs.service.SouvenirService;
import gfl.havryliuk.souvenirs.util.ConsoleReader;
import gfl.havryliuk.souvenirs.util.validation.ValidationPattern;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
public class UpdateSouvenir implements Action {
    @Override
    public void execute() {
        Optional<Souvenir> producerOptional = new ReturnableSouvenirSelectMenu().executeAndReturn();
        if (producerOptional.isPresent()) {
            Souvenir souvenir = producerOptional.get();

            String name = ConsoleReader.readForUpdatingString(souvenir.getName(), "souvenir name",
                    ValidationPattern.NAME);
            double price = ConsoleReader.readForUpdatingDouble(souvenir.getPrice(), "the price",
                    ValidationPattern.PRICE);

            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
            String savedDate = souvenir.getProductionDate().format(formatter);
            String datePattern = ConsoleReader.readForUpdatingString(savedDate, "production date in pattern yyyy-mm-dd",
                    ValidationPattern.DATE);
            LocalDateTime productionDate = LocalDateTime.parse(datePattern + "T00:00:00");

            souvenir.setName(name);
            souvenir.setPrice(price);
            souvenir.setProductionDate(productionDate);

            SouvenirService service = new SouvenirService();
            service.update(souvenir);
            log.info("{} updated.", souvenir);
        } else {
            log.info("Producer hasn't been found.");
        }
    }
}
