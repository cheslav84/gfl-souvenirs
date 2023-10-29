package gfl.havryliuk.souvenirs.presenter.action.producer;

import gfl.havryliuk.souvenirs.entities.Entity;
import gfl.havryliuk.souvenirs.service.ProducerService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Slf4j
public class DisplayAllWithSouvenirs extends EntityDisplayer {

    @Override
    protected List<? extends Entity> setEntities() {
        return  new ProducerService().getAllWithSouvenirs();
    }

}
