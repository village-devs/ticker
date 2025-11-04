package villagedevs.statemachine.task;

import villagedevs.statemachine.interfaces.State;

import java.util.HashSet;
import java.util.Set;

public enum TaskState implements State {
    OPENED,
    DONE;

    private final Set<TaskState> transitions = new HashSet<>();

    public TaskState addTransition(TaskState state) {
        this.transitions.add(state);
        return this;
    }

    public Set<TaskState> getValidTransitions() {
        return this.transitions;
    }

}
