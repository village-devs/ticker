package villagedevs.statemachine.manager;

import villagedevs.statemachine.job.Job;
import villagedevs.statemachine.state.State;

/**
 * Interface for a state manager.
 */
public interface StateManager {

    /**
     * Publishes a state for a job for move job to next state.
     *
     * @param job   The job to publish the state for.
     * @param state Net state to publish.
     */
    <T extends State> void publishSate(Job<T> job, T state);
}
