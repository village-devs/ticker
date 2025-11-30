package villagedevs.statemachine;

import villagedevs.statemachine.job.Job;
import villagedevs.statemachine.manager.StateManager;
import villagedevs.statemachine.state.State;
import villagedevs.statemachine.state.StateTransition;
import villagedevs.statemachine.state.StateTransitionResult;

import java.util.Map;

public class StateMachineManager implements StateManager {

    private final Map<Class<? extends Job>, StateTransition> stateServiceMap;

    public StateMachineManager(Map<Class<? extends Job>, StateTransition> stateServiceMap) {
        this.stateServiceMap = stateServiceMap;
    }

    @Override
    public <T extends State>StateTransitionResult publishSate(Job<T> job, T state) {
        //check exist
        StateTransition stateTransitionService = stateServiceMap.get(job.getClass());
        //npe
        return stateTransitionService.process(job, state);
    }

}
