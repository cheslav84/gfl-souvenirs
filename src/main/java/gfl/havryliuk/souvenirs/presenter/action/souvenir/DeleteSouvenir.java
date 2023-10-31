package gfl.havryliuk.souvenirs.presenter.action.souvenir;

import gfl.havryliuk.souvenirs.entities.Souvenir;
import gfl.havryliuk.souvenirs.presenter.MenuDisplayer;
import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.presenter.action.menu.ReturnableSouvenirSelectMenu;
import gfl.havryliuk.souvenirs.service.SouvenirService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
public class DeleteSouvenir implements Action {
    @Override
    public void execute() {
        Optional<Souvenir> souvenirOptional = new ReturnableSouvenirSelectMenu().executeAndReturn();
        if (souvenirOptional.isPresent()) {
            Souvenir souvenir = souvenirOptional.get();
            if (isRemovingConfirmed(souvenir)) {
                new SouvenirService().delete(souvenir);
                log.info("{} removed.", souvenir);
            } else  {
                log.info("Removing {} canceled.", souvenir);
            }
        } else {
            log.info("Souvenir hasn't been found.");
        }
    }

    private boolean isRemovingConfirmed(Souvenir souvenir) {
        log.warn("Do you really want to remove {}?", souvenir);
        List<String> menuItems = List.of("no", "yes");
        return MenuDisplayer.showItemsAndGetAnswer(menuItems) == 1;
    }
}
