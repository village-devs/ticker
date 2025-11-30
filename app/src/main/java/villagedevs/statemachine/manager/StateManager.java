package villagedevs.statemachine.manager;

import villagedevs.statemachine.job.Job;
import villagedevs.statemachine.state.State;
import villagedevs.statemachine.state.StateTransitionResult;

public interface StateManager {

    <T extends State>StateTransitionResult publishSate(Job<T> job, T state);
}
