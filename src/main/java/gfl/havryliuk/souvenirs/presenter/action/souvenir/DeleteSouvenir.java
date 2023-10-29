package gfl.havryliuk.souvenirs.presenter.action.souvenir;

import gfl.havryliuk.souvenirs.entities.Souvenir;
import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.presenter.action.menu.ReturnableSouvenirSelectMenu;
import gfl.havryliuk.souvenirs.service.SouvenirService;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class DeleteSouvenir implements Action {
    @Override
    public void execute() {
        Optional<Souvenir> producerOptional = new ReturnableSouvenirSelectMenu().executeAndReturn();
        if (producerOptional.isPresent()) {
            Souvenir souvenir = producerOptional.get();

            //todo confirm delete

            SouvenirService service = new SouvenirService();
            service.delete(souvenir);

            log.info("{} updated.", souvenir);
        } else {
            log.info("Souvenir hasn't been found.");
        }
    }
}
