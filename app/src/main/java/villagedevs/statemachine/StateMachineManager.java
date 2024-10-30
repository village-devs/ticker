package villagedevs.statemachine;

import java.util.HashMap;
import java.util.Map;

public class StateMachineManager {

    private Map<Class<? extends Job>, StateService> stateServiceMap;

    public StateMachineManager() {
        stateServiceMap = new HashMap<>();
        stateServiceMap.put(Task.class, new TaskStateService());
    }

    public void publishSate(Job job, State state) {
        StateService stateService = stateServiceMap.get(job.getClass());//npe
        stateService.process(job, state);
    }

}
