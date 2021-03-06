package learn.mastery;

import learn.mastery.data.DataAccessException;
import learn.mastery.ui.Controller;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class app {
    public static void main(String[] args) throws DataAccessException {
        ApplicationContext container = new ClassPathXmlApplicationContext("dependency-config.xml");

        Controller controller = container.getBean(Controller.class);
        controller.run();
    }
}
