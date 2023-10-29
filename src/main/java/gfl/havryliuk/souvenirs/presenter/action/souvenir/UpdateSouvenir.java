package gfl.havryliuk.souvenirs.presenter.action.souvenir;

import gfl.havryliuk.souvenirs.entities.Souvenir;
import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.presenter.action.menu.ReturnableSouvenirSelectMenu;
import gfl.havryliuk.souvenirs.service.ProducerService;
import gfl.havryliuk.souvenirs.service.SouvenirService;
import gfl.havryliuk.souvenirs.util.ConsoleReader;
import gfl.havryliuk.souvenirs.util.validation.ValidationPattern;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class UpdateSouvenir implements Action {
    @Override
    public void execute() {
        Optional<Souvenir> producerOptional = new ReturnableSouvenirSelectMenu().executeAndReturn();
        if (producerOptional.isPresent()) {
            Souvenir souvenir = producerOptional.get();

            String name = ConsoleReader.readForUpdatingString(souvenir.getName(), "producer name", ValidationPattern.NAME);

            souvenir.setName(name);

            SouvenirService service = new SouvenirService();
            service.update(souvenir);
            log.info("{} updated.", souvenir);
        } else {
            log.info("Producer hasn't been found.");
        }
    }
}
