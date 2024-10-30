package villagedevs.statemachine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public enum TaskState implements State {
    OPENED,
    DONE;

    private Map<TaskState, Set<TaskState>> transitions = new HashMap<>();

    TaskState() {
        transitions.put(this, new HashSet<>());
    }

    private TaskState addTransition(TaskState state) {
        Set<TaskState> taskStates = transitions.get(this);
        taskStates.add(state);
        return this;
    }

    public Set<TaskState> getValidTransitions() {
        return transitions.get(this);
    }

    public static void init() {
        OPENED
            .addTransition(DONE)
            .addTransition(OPENED);
    }

}
