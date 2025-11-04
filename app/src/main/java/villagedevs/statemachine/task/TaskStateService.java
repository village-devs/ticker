package villagedevs.statemachine.task;

import villagedevs.statemachine.annotation.StateServiceJob;
import villagedevs.statemachine.interfaces.State;
import villagedevs.statemachine.interfaces.StateHandler;
import villagedevs.statemachine.interfaces.StateService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static villagedevs.statemachine.task.TaskState.DONE;
import static villagedevs.statemachine.task.TaskState.OPENED;

@StateServiceJob
public class TaskStateService implements StateService<Task, TaskState> {

    //spring init
    private final Map<State, StateHandler<Task, TaskState>> handlerList = new HashMap<>();

    public TaskStateService() {
        initTransitions();
    }

    private static void initTransitions() {
        OPENED.addTransition(DONE)
                .addTransition(OPENED);

        DONE.addTransition(OPENED);
    }

    @Override
    public void addHandler(TaskState state, StateHandler<Task, TaskState> handler) {
        handlerList.put(state, handler);
    }

    @Override
    public boolean checkTransition(Task task, TaskState toState) {
        if (task.state.getValidTransitions().contains(toState)) {
            return true;
        }
        return false;
        //or else throw exception
    }

    @Override
    public void process(Task task, TaskState taskState) {
        checkTransition(task, taskState);
        //check type
        StateHandler<Task, TaskState> stateHandler = handlerList.get(taskState);
        //npe
        stateHandler.handle(task, taskState);
    }
}
