package villagedevs.statemachine.task;

import villagedevs.statemachine.State;
import villagedevs.statemachine.StateHandler;
import villagedevs.statemachine.StateService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static villagedevs.statemachine.task.TaskState.DONE;
import static villagedevs.statemachine.task.TaskState.OPENED;

public class TaskStateService implements StateService<Task, TaskState> {

    private final Map<State, StateHandler<Task, TaskState>> handlerList;

    //private Map<TaskState, Set<TaskState>> transitions;


    public TaskStateService() {
        //spring init
        handlerList = new HashMap<>();
        handlerList.put(DONE, new TaskStateDoneHandler());
        handlerList.put(OPENED, new TaskStateOpenedHandler());

        OPENED.addTransition(DONE).addTransition(OPENED);

        DONE.addTransition(OPENED);
        //  transitions = new HashMap<>();
        // this.addTransition(DONE, OPENED);
        // this.addTransition(OPENED, OPENED);
        //TaskState.init(); //так нельзя говно оно должно само
    }

    //private void addTransition(TaskState toState, TaskState fromState) {
    //Set<TaskState> taskStates = transitions.get(fromState);
    //taskStates.add(toState);
    //}


    @Override
    public boolean checkTransition(Task task, TaskState toState) {
        TaskState stateFrom = task.state;

        // = transitions.get(stateFrom);
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
        StateHandler<Task, TaskState> stateHandler = handlerList.get(taskState);
        //npe
        stateHandler.handle(task, taskState);
    }


}
