package villagedevs.statemachine;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TaskStateService implements StateService<Task, TaskState> {

    private Map<Class<? extends Job>, StateHandler> handlerList;

    public TaskStateService() {
        handlerList = new HashMap<>();
        handlerList.put(Task.class, new TaskStateDoneHandler());
        //TaskState.init(); //так нельзя говно оно должно само
    }


    @Override
    public boolean checkTransition(Task task, TaskState toState) {
        TaskState stateFrom = task.state;
        Set<TaskState> possibleTransitions = stateFrom.getValidTransitions();
        if (possibleTransitions.contains(toState)) {
            return true;
        }
        return false;
        //or else throw exception
    }

    @Override
    public void process(Task task, TaskState taskState) {
        checkTransition(task, taskState);
        StateHandler stateHandler = handlerList.get(task.getClass());
        stateHandler.handle(task, taskState);
    }


}
