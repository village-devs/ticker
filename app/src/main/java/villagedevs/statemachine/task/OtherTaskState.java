package villagedevs.statemachine.task;

import villagedevs.statemachine.state.State;

import java.util.HashSet;
import java.util.Set;

public enum OtherTaskState implements State {
    READY,
    IN_WORK,
    CLOSE;

    private final Set<OtherTaskState> transitions = new HashSet<>();

    public OtherTaskState addTransition(OtherTaskState state) {
        this.transitions.add(state);
        return this;
    }

    public Set<OtherTaskState> getValidTransitions() {
        return this.transitions;
    }

}
