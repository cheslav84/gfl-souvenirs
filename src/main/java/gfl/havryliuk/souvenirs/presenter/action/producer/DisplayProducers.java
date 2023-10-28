package gfl.havryliuk.souvenirs.presenter.action.producer;

import gfl.havryliuk.souvenirs.entities.Producer;
import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.service.ProducerService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class DisplayProducers implements Action {

    public List<Producer> getEntities() {
        ProducerService service = new ProducerService();
        return service.getAll();//todo подумати, тут відрізнятиметься від інших
    }


    @Override
    public void execute() {

    }


}
