package villagedevs.statemachine.common;

import org.apache.commons.collections4.CollectionUtils;
import villagedevs.statemachine.common.result.CommonStateTransitionResult;
import villagedevs.statemachine.job.Job;
import villagedevs.statemachine.state.State;
import villagedevs.statemachine.state.StateHandler;
import villagedevs.statemachine.state.StateTransition;

import java.util.Map;
import java.util.Optional;
import java.util.Set;


public abstract class CommonStateTransitionService<T extends Job<K>, K extends State> implements StateTransition<T, K> {

    private final Map<K, StateHandler<T, K>> handlerList;

    private final Map<K, Set<K>> transitions;

    public CommonStateTransitionService(Map<K, StateHandler<T, K>> handlerList) {
        this.handlerList = handlerList;
        this.transitions = getTransitions();
    }


    @Override
    public boolean checkTransition(T task, K toState) {
        Set<K> transition = transitions.get(task.getState());
        if (CollectionUtils.isEmpty(transition)) {
            return false;
        }

        return transition.contains(toState);
    }

    @Override
    public CommonStateTransitionResult process(T task, K taskState) {
        try {
            if (checkTransition(task, taskState)) {
                getStateHandler(taskState)
                        .ifPresent(stateHandler -> stateHandler.handle(task, taskState));
                return new CommonStateTransitionResult(true);
            }
            return new CommonStateTransitionResult(false);

        } catch (Exception e) {
            //TODO: log error
            return new CommonStateTransitionResult(false);
        }
    }

    @Override
    public Optional<StateHandler<T, K>> getStateHandler(K taskState) {
        return Optional.ofNullable(handlerList.get(taskState));
    }

}
