package villagedevs.statemachine;

import villagedevs.statemachine.story.Story;
import villagedevs.statemachine.story.StoryStateService;
import villagedevs.statemachine.task.Task;
import villagedevs.statemachine.task.TaskStateService;

import java.util.HashMap;
import java.util.Map;

public class StateMachineManager {

    private Map<Class<? extends Job>, StateService> stateServiceMap;

    public StateMachineManager() {
        //spring init
        stateServiceMap = new HashMap<>();
        stateServiceMap.put(Task.class, new TaskStateService());
        stateServiceMap.put(Story.class, new StoryStateService());
    }

    public void publishSate(Job job, State state) {
        StateService stateService = stateServiceMap.get(job.getClass());
        //npe
        stateService.process(job, state);
    }

}
