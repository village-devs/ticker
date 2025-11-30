package villagedevs.statemachine.common;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import villagedevs.statemachine.job.Job;
import villagedevs.statemachine.state.State;
import villagedevs.statemachine.state.StateHandler;
import villagedevs.statemachine.state.StateTransition;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Common implementation of StateTransition with a state transition logic
 *
 * @param <JOB> Type of Job
 * @param <STATE> Type of State
 */
public abstract class CommonStateTransitionService<JOB extends Job<STATE>, STATE extends State> implements StateTransition<JOB, STATE> {
    private static Logger logger = LoggerFactory.getLogger(CommonStateTransitionService.class);

    private final Map<STATE, StateHandler<JOB, STATE>> handlerList;

    private final Map<STATE, Set<STATE>> transitions;

    public CommonStateTransitionService(Map<STATE, StateHandler<JOB, STATE>> handlerList) {
        this.handlerList = handlerList;
        this.transitions = getTransitions();
    }


    @Override
    public boolean checkTransition(JOB task, STATE toState) {
        Set<STATE> transition = transitions.get(task.getState());
        if (CollectionUtils.isEmpty(transition)) {
            return false;
        }

        return transition.contains(toState);
    }

    @Override
    public void process(JOB task, STATE taskState) {
        try {
            if (checkTransition(task, taskState)) {
                Optional<StateHandler<JOB, STATE>> stateHandler = getStateHandler(taskState);
                if (stateHandler.isPresent()) {
                    stateHandler.ifPresent(handler -> handler.handle(task, taskState));
                } else {
                    logger.warn("No custom state handler found for state {}, call default state handler", taskState);
                    getDefaultStateHandler().ifPresent(handler -> handler.handle(task, taskState));
                }
            }

        } catch (Exception e) {
            logger.error("Error while processing state transition", e);
            getErrorStateHandler().ifPresent(handler -> handler.handle(task, taskState));
        }
    }

    @Override
    public Optional<StateHandler<JOB, STATE>> getStateHandler(STATE taskState) {
        return Optional.ofNullable(handlerList.get(taskState));
    }

}
