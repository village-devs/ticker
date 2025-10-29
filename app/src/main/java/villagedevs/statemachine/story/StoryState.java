package villagedevs.statemachine.story;

import villagedevs.statemachine.State;

import java.util.HashSet;
import java.util.Set;

public enum StoryState implements State {
    OPENED,
    IN_PROGRESS,
    DONE;

    private final Set<StoryState> transitions = new HashSet<>();

    public StoryState addTransition(StoryState state) {
        this.transitions.add(state);
        return this;
    }

    public Set<StoryState> getValidTransitions() {
        return this.transitions;
    }

}
