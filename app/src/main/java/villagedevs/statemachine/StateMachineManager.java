package villagedevs.statemachine;

import villagedevs.statemachine.interfaces.Job;
import villagedevs.statemachine.interfaces.State;
import villagedevs.statemachine.interfaces.StateService;

import java.util.HashMap;
import java.util.Map;

public class StateMachineManager {

    //spring init
    private Map<Class<? extends Job>, StateService> stateServiceMap = new HashMap<>();

    public void addStateService(Class<? extends Job> job, StateService stateService) {
        stateServiceMap.put(job, stateService);
    }

    public void publishSate(Job job, State state) {
        //check exist
        StateService stateService = stateServiceMap.get(job.getClass());
        //npe
        stateService.process(job, state);
    }

}
