package statemachine;

import Configs.StateMachineConfig;
import events.Events;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.statemachine.StateMachine;
import states.States;

/**
 * @author Dhiraj
 * @date 03/10/19
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext=new AnnotationConfigApplicationContext(StateMachineConfig.class);
        System.out.println(applicationContext);
        StateMachine<States, Events> stateMachine=applicationContext.getBean(StateMachine.class);
        stateMachine.start();
        stateMachine.sendEvent(Events.E1);
        stateMachine.sendEvent(Events.E2);
    }
}
