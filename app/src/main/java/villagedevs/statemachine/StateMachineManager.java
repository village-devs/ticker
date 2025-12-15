package villagedevs.statemachine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import villagedevs.statemachine.job.Job;
import villagedevs.statemachine.manager.StateManager;
import villagedevs.statemachine.state.State;
import villagedevs.statemachine.state.StateTransition;

import java.util.Map;

public class StateMachineManager implements StateManager {

    private static Logger logger = LoggerFactory.getLogger(StateMachineManager.class);

    private final Map<Class<? extends Job>, StateTransition> stateServiceMap;

    public StateMachineManager(Map<Class<? extends Job>, StateTransition> stateServiceMap) {
        this.stateServiceMap = stateServiceMap;
    }

    @Override
    public <T extends State> void publishSate(Job<T> job, T state) {
        StateTransition stateTransition = stateServiceMap.get(job.getClass());
        if (stateTransition == null) {
            logger.error("No state transition found for job: {}", job.getClass().getName());
            return;
        }

        stateTransition.process(job, state);
    }

}
