package Configs;

import events.Events;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;
import states.States;

import java.util.EnumSet;

/**
 * @author Dhiraj
 * @date 03/10/19
 */

@Configuration
@ComponentScan({"configs", "events", "statemachine", "states"})
@EnableStateMachine
public class StateMachineConfig
        extends EnumStateMachineConfigurerAdapter<States, Events> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config)
            throws Exception {
        config
                .withConfiguration()
                .autoStartup(false)
                .listener(listener());
    }

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states)
            throws Exception {
        states
                .withStates()
                .initial(States.SI)
                .states(EnumSet.allOf(States.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions)
            throws Exception {
        transitions
                .withExternal()
                .source(States.SI).target(States.S1).event(Events.E1)
                .and()
                .withExternal()
                .source(States.S1).target(States.S2).event(Events.E2);
    }

    @Bean
    public StateMachineListener<States, Events> listener() {
        return new StateMachineListenerAdapter<States, Events>() {
            public void stateChanged(State<States, Events>from, State<States, Events> to) {
                System.out.println( "stateChanged>"+from+">>>>"+to);
                System.out.println( "State change to " + to.getId());

            }

            public void stateEntered(State<States, Events> state) {
                System.out.println( "stateEntered>>>>>"+state);
            }

            public void stateExited(State<States, Events> state) {
                System.out.println( "stateExited>>>>>"+state);
            }

            public void eventNotAccepted(Message<Events> event) {
                System.out.println( "eventNotAccepted>>>>"+event);
            }

            public void transition(Transition<States, Events> transition) {
                System.out.println( "transition>>>>>"+transition);
            }

            public void transitionStarted(Transition<States, Events> transition) {
                System.out.println( "transitionStarted>>>>"+transition);
                System.out.println(transition.getSource());
                System.out.println(transition.getActions());
            }

            public void transitionEnded(Transition<States, Events> transition) {
                System.out.println( "transitionEnded>>>>>"+transition);
            }

            public void stateMachineStarted(StateMachine<States, Events> stateMachine) {
                System.out.println( "stateMachineStarted>>>>>"+stateMachine);
            }

            public void stateMachineStopped(StateMachine<States, Events> stateMachine) {
                System.out.println( "stateMachineStopped>>>>"+stateMachine);
            }

            public void stateMachineError(StateMachine<States, Events> stateMachine, Exception exception) {
                System.out.println( "stateMachineError>>>>"+stateMachine+">>>"+exception);
            }

            public void extendedStateChanged(Object key, Object value) {
                System.out.println( "extendedStateChanged>>>"+key+">>>>"+value);
            }
        };
    }


}
