package villagedevs.statemachine.story;

import villagedevs.statemachine.State;
import villagedevs.statemachine.StateHandler;
import villagedevs.statemachine.StateService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static villagedevs.statemachine.story.StoryState.IN_PROGRESS;
import static villagedevs.statemachine.story.StoryState.DONE;
import static villagedevs.statemachine.story.StoryState.OPENED;

public class StoryStateService implements StateService<Story, StoryState> {

    private final Map<State, StateHandler<Story, StoryState>> handlerList;

    //private Map<TaskState, Set<TaskState>> transitions;

    public StoryStateService() {
        //spring init
        handlerList = new HashMap<>();
        handlerList.put(DONE, new StoryStateDoneHandler());
        handlerList.put(IN_PROGRESS, new StoryStateInProgressHandler());
        handlerList.put(OPENED, new StoryStateOpenedHandler());

        initTransitions();
        //  transitions = new HashMap<>();
        // this.addTransition(DONE, OPENED);
        // this.addTransition(OPENED, OPENED);
        //TaskState.init(); //так нельзя говно оно должно само
    }

    private static void initTransitions() {
        OPENED.addTransition(IN_PROGRESS)
                .addTransition(OPENED)
                .addTransition(DONE);

        IN_PROGRESS
                .addTransition(DONE);

        DONE
                .addTransition(OPENED);
    }

    //private void addTransition(TaskState toState, TaskState fromState) {
    //Set<TaskState> taskStates = transitions.get(fromState);
    //taskStates.add(toState);
    //}


    @Override
    public boolean checkTransition(Story task, StoryState toState) {
        StoryState stateFrom = task.state;

        // = transitions.get(stateFrom);
        Set<StoryState> possibleTransitions = stateFrom.getValidTransitions();
        if (possibleTransitions.contains(toState)) {
            return true;
        }
        return false;
        //or else throw exception
    }

    @Override
    public void process(Story task, StoryState taskState) {
        checkTransition(task, taskState);
        StateHandler<Story, StoryState> stateHandler = handlerList.get(taskState);
        //npe
        stateHandler.handle(task, taskState);
    }


}
