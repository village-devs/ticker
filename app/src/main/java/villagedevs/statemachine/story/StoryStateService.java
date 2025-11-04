package villagedevs.statemachine.story;

import villagedevs.statemachine.annotation.StateServiceJob;
import villagedevs.statemachine.interfaces.State;
import villagedevs.statemachine.interfaces.StateHandler;
import villagedevs.statemachine.interfaces.StateService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static villagedevs.statemachine.story.StoryState.IN_PROGRESS;
import static villagedevs.statemachine.story.StoryState.DONE;
import static villagedevs.statemachine.story.StoryState.OPENED;

@StateServiceJob
public class StoryStateService implements StateService<Story, StoryState> {

    //spring init
    private final Map<State, StateHandler<Story, StoryState>> handlerList = new HashMap<>();

    public StoryStateService() {
        initTransitions();
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

    @Override
    public boolean checkTransition(Story task, StoryState toState) {
        if (task.state.getValidTransitions().contains(toState)) {
            return true;
        }
        return false;
        //or else throw exception
    }

    @Override
    public void process(Story task, StoryState taskState) {
        checkTransition(task, taskState);
        //check state handler class instance of
        StateHandler<Story, StoryState> stateHandler = handlerList.get(taskState);
        //npe
        stateHandler.handle(task, taskState);
    }

    @Override
    public void addHandler(StoryState state, StateHandler<Story, StoryState> handler) {
        handlerList.put(state, handler);
    }
}
