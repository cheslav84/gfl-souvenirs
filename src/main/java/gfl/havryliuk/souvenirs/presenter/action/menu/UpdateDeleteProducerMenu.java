package gfl.havryliuk.souvenirs.presenter.action.menu;

import gfl.havryliuk.souvenirs.entities.Entity;
import gfl.havryliuk.souvenirs.presenter.action.Action;
import gfl.havryliuk.souvenirs.presenter.action.EmptyAction;
import gfl.havryliuk.souvenirs.presenter.action.producer.DeleteProducer;
import gfl.havryliuk.souvenirs.presenter.action.producer.UpdateProducer;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;


public class UpdateDeleteProducerMenu extends MenuTemplate {

    public List<MenuAction> getActionList() {
//        return Arrays.asList(ActionList.values());
        return null;
    }



    @Getter
    @AllArgsConstructor
    private enum ActionList implements MenuActionEntity {
        DO_NOTHING("Do nothing", new EmptyAction()),
        UPDATE_PRODUCER("Update producer", new UpdateProducer()),
        DELETE_PRODUCER("Delete producer", new DeleteProducer());

        private final String menuItem;
        private final Action action;

        public Entity execute(Entity entity) {
            System.out.println(entity);

            //todo if - inject producer
            Action toExecute = action;
            Class<? extends Action> aClass = toExecute.getClass();
            try {
//                if(aClass.getClass() instanceof UpdateProducer.class) {
                if(toExecute instanceof UpdateProducer || toExecute instanceof DeleteProducer) {
                Field producerField = aClass.getDeclaredField("Producer");
//                producer =  getMenuItem();
//                    Class<?> clazz = field.getType();

//                    Object annotatedObject = aClass.getConstructor().newInstance();

                    producerField.set(action, entity);


                    System.out.println("Producer");

                }

            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            action.execute();
            return entity;
        }

    }
}
